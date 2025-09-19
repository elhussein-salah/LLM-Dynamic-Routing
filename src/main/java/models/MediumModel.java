package models;

import domain.UserQuery;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class MediumModel implements Model {
    private final OllamaChatModel model;

    public MediumModel(String baseUrl, String modelName) {
        this.model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }

    @Override
    public String answer(UserQuery query) {
        return model.generate("Provide clear explanation: " + query.getText());
    }
}