# Start with a base image that has Java and Maven
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory
WORKDIR /app

# === START OF DEFINITIVE FIX ===
# Install required system dependencies for Google Chrome
# This is the crucial step that fixes the "Driver server process died" error
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    # Install Chrome browser
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y \
    google-chrome-stable \
    firefox-esr \
    # Clean up the cache to keep the image size small
    && rm -rf /var/lib/apt/lists/*
# === END OF DEFINITIVE FIX ===

# Copy the pom.xml and download dependencies to leverage Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project source code
COPY . .

# The command to execute when the container starts
CMD ["mvn", "install"]