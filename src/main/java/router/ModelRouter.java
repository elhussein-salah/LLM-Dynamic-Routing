package router;

import domain.UserQuery;
import models.AdvancedModel;
import models.MediumModel;
import models.SimpleModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ModelRouter {

    private final OllamaChatModel routerModel;
    private final SimpleModel simple;
    private final MediumModel medium;
    private final AdvancedModel advanced;

    public ModelRouter(OllamaChatModel routerModel,
                       SimpleModel simple,
                       MediumModel medium,
                       AdvancedModel advanced) {
        this.routerModel = routerModel;
        this.simple = simple;
        this.medium = medium;
        this.advanced = advanced;
    }

    public RouteResult route(UserQuery query) {
        String decision = routerModel.generate(
                "Please classify the following user query into one of these categories [SIMPLE, MEDIUM, ADVANCED]. " +
                        "Only respond with one of the category names in uppercase. Do not add any explanation.\n\nQuery: " + query.getText());

        if (decision.contains("SIMPLE")) {
            return new RouteResult(simple.answer(query), "SIMPLE");
        } else if (decision.contains("MEDIUM")) {
            return new RouteResult(medium.answer(query), "MEDIUM");
        } else  {
            return new RouteResult(advanced.answer(query), "ADVANCED");
        }
    }
}
