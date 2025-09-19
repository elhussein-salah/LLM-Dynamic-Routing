package models;

import domain.UserQuery;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class AdvancedModel implements Model {
    private final OllamaChatModel model;

    public AdvancedModel(String baseUrl, String modelName) {
        this.model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }

    @Override
    public String answer(UserQuery query) {
        return model.generate("Give step-by-step analysis: " + query.getText());
    }
}