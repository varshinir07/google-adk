package com.adk.googleadktools;


import com.adk.googleadktools.tools.AdkTools;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.adk.tools.FunctionTool;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GoogleAdkToolsApplication {

    private static String USER_ID = "varshini";
    private static String NAME = "tool_agent";

    public static BaseAgent ROOT_AGENT = initAgent();

    public static BaseAgent initAgent() {
        return LlmAgent.builder()
                .name(NAME)
                .model("gemini-2.0-flash")
                .description("Agent to answer questions about the time in a city.")
                .instruction(
                        "You are a helpful agent who can answer user questions about the time in a city.")
                .tools(FunctionTool.create(AdkTools.class, "getCurrentTime"))
                .outputKey("answer")
                .build();
    }

    public static void main(String[] args) {

        InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        Session session = runner
                .sessionService()
                .createSession(runner.appName(), USER_ID)
                .blockingGet();

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.print("\nYou > ");
                if (!scanner.hasNextLine()) {
                    System.out.println("No input detected. Exiting...");
                    break;
                }
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                Content userMsg = Content.fromParts(Part.fromText(userInput));
                Flowable<Event> events = runner
                        .runAsync(session.userId(), session.id(), userMsg);

                System.out.println("\nAgent >");
                events.blockingForEach(event -> {
                    System.out.println(event.stringifyContent());
                });
            }
        }
    }

}
