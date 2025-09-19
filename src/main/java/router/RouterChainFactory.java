package router;

import models.SimpleModel;
import models.MediumModel;
import models.AdvancedModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class RouterChainFactory {

    public static ModelRouter createRouterChain(
            String ollamaBaseUrl, String routerModelName,
            SimpleModel simple, MediumModel medium,
            AdvancedModel advanced) {

        OllamaChatModel routerModel = OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(routerModelName)
                .build();

        return new ModelRouter(routerModel, simple, medium, advanced);
    }
}