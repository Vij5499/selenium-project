# Start with a base image that has Java and Maven
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory
WORKDIR /app

# Install all necessary system dependencies for Chrome and Firefox
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    # Install Chrome
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    # Install Firefox
    && apt-get update && apt-get install -y \
    google-chrome-stable \
    firefox-esr \
    # Clean up
    && rm -rf /var/lib/apt/lists/*

# Copy the pom.xml and download dependencies for caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project source code
COPY . .

# Run the tests directly. This is simpler and more stable for CI.
CMD ["mvn", "install"]