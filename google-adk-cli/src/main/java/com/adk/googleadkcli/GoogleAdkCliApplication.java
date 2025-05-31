package com.adk.googleadkcli;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GoogleAdkCliApplication {

    public static BaseAgent ROOT_AGENT = initAgent();

    public static BaseAgent initAgent(){

        return LlmAgent.builder()
                .name("Science-app")
                .description("Science Teacher Agent")
                .model("gemini-2.0-flash")
                .instruction(
                        """
                        You are helpful science teacher taht explains science concepts to kids
                        """
                )
                .outputKey("answer")
                .build();
    }

    public static void main(String[] args) {

        InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        Session session = runner
                .sessionService()
                .createSession(runner.appName(), "user")
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
