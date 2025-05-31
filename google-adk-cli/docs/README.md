# Google ADK CLI Application

This project is a Command-Line Interface (CLI) application built using **Java**, **Spring Boot**, and **Google ADK**. The application leverages the **Google ADK** framework to create an AI-powered agent that acts as a science teacher, explaining science concepts to kids.

## Features
- Uses **Google ADK** to create an AI agent (`Science-app`) with the `gemini-2.0-flash` model.
- Provides a conversational interface for users to interact with the agent.
- Processes user input and generates responses in real-time.

## Prerequisites
1. **Java 21**: Ensure you have Java 21 installed. The project uses Gradle's Java toolchain to enforce this version.
2. **Gradle**: Ensure Gradle is installed or use the provided `gradlew` script.
3. **Google API Key**: Obtain an API key from Google to use the **Google ADK** services.

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. Export the required environment variables:
   ```bash
   export GOOGLE_GENAI_USE_VERTEXAI=FALSE
   export GOOGLE_API_KEY=PASTE_YOUR_ACTUAL_API_KEY_HERE
   ```

3. Build the project:
   ```bash
   ./gradlew build
   ```

4. Run the application:
   ```bash
   ./gradlew run
   ```

## Usage
1. After running the application, you will see a prompt:
   ```
   You >
   ```
2. Type your question or input for the science teacher agent.
3. The agent will respond with an explanation:
   ```
   Agent >
   [Response from the agent]
   ```
4. To exit the application, type `exit`.

## Reference Documentation
For more details on **Google ADK**, refer to the [Google ADK Quickstart Guide](https://google.github.io/adk-docs/get-started/quickstart/).