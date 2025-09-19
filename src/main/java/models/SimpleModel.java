package models;

import domain.UserQuery;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class SimpleModel implements Model {
    private final OllamaChatModel model;

    public SimpleModel(String baseUrl, String modelName) {
        this.model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }

    @Override
    public String answer(UserQuery query) {
        return model.generate("Answer briefly: " + query.getText());
    }
}