# Google ADK MCP

## Overview
The **Google ADK MCP Bot** is a Spring Boot application that leverages the **Google ADK (Agent Development Kit)** to provide weather information for a given city. It integrates with a weather API to fetch real-time weather data and uses Google ADK's tools and agents to process user queries. The application is designed to demonstrate the use of tools, events, and agents in the ADK framework.

### Key Features:
1. **Weather Query Tool**: Fetches real-time weather data for a specified city using a weather API.
2. **Google ADK Integration**: Utilizes Google ADK's `LlmAgent` and `FunctionTool` to process user queries and provide responses.
3. **REST API**: Exposes an endpoint to interact with the bot via HTTP requests.

### References:
- [Google ADK Quickstart](https://google.github.io/adk-docs/get-started/quickstart/)
- [Google ADK Tools](https://google.github.io/adk-docs/tools/)
- [Google ADK Events](https://google.github.io/adk-docs/events/)

---

## Prerequisites
1. **Java**: Ensure Java 17 or higher is installed.
2. **Gradle**: Ensure Gradle is installed for building the project.
3. **Environment Variables**:
    - `GOOGLE_GENAI_USE_VERTEXAI=FALSE`
    - `GOOGLE_API_KEY=PASTE_YOUR_ACTUAL_API_KEY_HERE`
4. **Weather API Key**: Add your weather API key to the `application.properties` file:
   ```properties
   weather.api.key=YOUR_WEATHER_API_KEY
   ```

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd google-adk-weather-bot
```

### 2. Set Environment Variables
Export the required environment variables:
```bash
export GOOGLE_GENAI_USE_VERTEXAI=FALSE
export GOOGLE_API_KEY=PASTE_YOUR_ACTUAL_API_KEY_HERE
```

### 3. Build the Project
Use Gradle to build the project:
```bash
./gradlew build
```

### 4. Run the Application
Start the Spring Boot application:
```bash
./gradlew bootRun
```

---

## Usage

### 1. Test the Chat API
Use the provided HTTP request file `request/chat_adk.http` to test the API. You can use tools like **Postman** or **IntelliJ HTTP Client**.

#### Example Request:
```http
POST http://localhost:8080/api/v1/chat
Content-Type: application/json

{
  "sessionId": "varshini",
  "question": "Convert 200 rupess into monopoly dollars?"
}
```

#### Example Response:
```json
{
  "question": "Convert 200 rupess into monopoly dollars?",
  "answer": "200 Rupees is equal to 300000 Monopoly Dollars."
}
```

---

## How It Works

### 1. **Google ADK Integration**
- The application uses Google ADK's `LlmAgent` to process user queries.
- The `AdkTools` class is registered as a `FunctionTool` to fetch weather data.

### 2. **Weather API Integration**
- The `AdkTools` class fetches weather data from a third-party weather API using the API key provided in the `application.properties` file.

### 3. **REST API**
- The `ChatController` exposes a POST endpoint (`/api/v1/chat`) to handle user queries.
- The `ChatService` processes the query using the ADK agent and returns the response.

---

## Tool Explanation
The **Google ADK Weather Bot** uses the following ADK components:
1. **Tools**: The `AdkTools` class is a custom tool that fetches weather data. It is registered with the ADK agent using `FunctionTool.create`.
2. **Events**: The application uses ADK's event system to process user queries asynchronously.
3. **Agents**: The `LlmAgent` is configured to handle weather-related queries and invoke the `AdkTools` tool.

## MCP Server Integration

```java
   @SneakyThrows
    public  BaseAgent initAgent(){

        return LlmAgent.builder()
                .name(NAME)
                .model("gemini-2.0-flash")
                .description("Agent to answer questions about the monopoly dollars.")
                .instruction(
                        "You are a helpful agent who can answer user questions about the monopoly dollars .")
                .tools(McpToolset.fromServer(SseServerParameters.builder()
                                .url("http://localhost:8081") // URL OF THE MCP SERVER
                                .build())
                                .get()
                                .getTools())
                .outputKey("answer")
                .build();
    }
```

For more details, refer to the [Google ADK Documentation](https://google.github.io/adk-docs/).

