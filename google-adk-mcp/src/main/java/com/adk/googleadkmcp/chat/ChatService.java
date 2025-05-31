package com.adk.googleadkmcp.chat;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.SseServerParameters;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private static String NAME = "tool_agent";

    public  BaseAgent ROOT_AGENT = initAgent();

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
