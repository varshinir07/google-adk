# Google ADK Tools CLI

## Overview

This project is a CLI application built using **Java 21**, **Spring Boot**, and **Google ADK**. It provides a tool to interact with a conversational agent (`tool_agent`) that can answer user questions about the current time in a specified city. The agent is powered by **Google ADK** and uses a custom tool (`AdkTools`) to fetch the current time for a given city.

## Features

- **Conversational Agent**: A chatbot that responds to user queries about the time in a city.
- **Custom Tool Integration**: Uses the `AdkTools` class to provide the `getCurrentTime` functionality.
- **Google ADK Integration**: Leverages Google ADK's `LlmAgent` and `InMemoryRunner` for agent execution.

## Prerequisites

1. **Java 21**: Ensure you have Java 21 installed.
2. **Gradle**: Build and run the project using Gradle.
3. **Google API Key**: Obtain a valid Google API key to use the Google ADK.

## Setup Instructions

1. Clone the repository and navigate to the project directory.
2. Set the following environment variables:
   ```bash
   export GOOGLE_GENAI_USE_VERTEXAI=FALSE
   export GOOGLE_API_KEY=PASTE_YOUR_ACTUAL_API_KEY_HERE
   ```
3. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```

## Running the CLI

1. Start the application:
   ```bash
   ./gradlew run
   ```
2. Interact with the agent:
    - Type your query (e.g., "What is the time in New York?").
    - Type `exit` to terminate the application.

## Tool Explanation

The tool definition in the `Tools Explanation` section refers to the `AdkTools` class, specifically the `getCurrentTime` method. Here's a breakdown of the code:

### Code Explanation
1. **Class Definition**:
    - The `AdkTools` class is defined in the `com.adk.googleadktools.tools` package.
    - It contains a static method `getCurrentTime` that serves as a tool for the conversational agent.

2. **Method `getCurrentTime`**:
    - **Input Parameter**:
        - The method takes a single parameter, `city`, which is annotated with `@Schema`. This annotation provides a description of the parameter, making it easier to document and understand its purpose.
    - **Functionality**:
        - The method returns a `Map` containing two key-value pairs:
            - `"city"`: The name of the city passed as input.
            - `"currentTime"`: A hardcoded value of `"12:00 PM"`, which represents the current time for the city.
    - **Purpose**:
        - This is a placeholder implementation. It does not fetch real-time data but demonstrates how a tool can be defined and used by the agent.

3. **Integration with the Agent**:
    - The `getCurrentTime` method is registered as a tool in the `GoogleAdkToolsApplication` class using `FunctionTool.create`. This allows the conversational agent to call the method when responding to user queries about the time in a city.

### Code
```java
package com.adk.googleadktools.tools;

import com.google.adk.tools.Annotations.Schema;

import java.util.Map;

public class AdkTools {

    public static Map<String, String> getCurrentTime(
            @Schema(description = "The name of the city for which to retrieve the current time")
            String city) {

        String currentTime = "12:00 PM"; // Placeholder time

        return Map.of("city", city, "currentTime", currentTime);
    }
}
```

This code defines the tool that the agent uses to provide responses about the time in a city. It can be extended to fetch real-time data from an external API.
### References

- [Google ADK Quickstart](https://google.github.io/adk-docs/get-started/quickstart/)
- [Google ADK Tools Documentation](https://google.github.io/adk-docs/tools/)