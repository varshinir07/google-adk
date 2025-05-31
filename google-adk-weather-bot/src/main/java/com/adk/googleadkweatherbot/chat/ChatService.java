package com.adk.googleadkweatherbot.chat;

import com.adk.googleadkweatherbot.tools.AdkTools;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.adk.tools.FunctionTool;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static String NAME = "tool_agent";

    public  BaseAgent ROOT_AGENT = initAgent();

    public  BaseAgent initAgent() {
        return LlmAgent.builder()
                .name(NAME)
                .model("gemini-2.0-flash")
                .description("Agent to answer questions about the weather in a city.")
                .instruction(
                        "You are a helpful agent who can answer user questions about the weather in a city.")
                .tools(FunctionTool.create(AdkTools.class, "getWeather"))
                .outputKey("answer")
                .build();
    }

    public String converse(ChatRequest chatRequest){

        String userId = chatRequest.sessionId();
        String question = chatRequest.question();
        StringBuilder response = new StringBuilder();


        InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        Session session = runner
                .sessionService()
                .createSession(runner.appName(), userId)
                .blockingGet();

        Content userMsg = Content.fromParts(Part.fromText(question));
        Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userMsg);

        events.blockingForEach(event -> {
            Content content = event.content().get();
            if(content.parts().get().get(0).text().isPresent()){
                response.append(content.parts().get().get(0).text().get());
            }
        });

        return response.toString();
    }
}
