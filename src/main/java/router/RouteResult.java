package router;

public class RouteResult {
    private final String response;
    private final String usedModel;

    public RouteResult(String response, String usedModel) {
        this.response = response;
        this.usedModel = usedModel;
    }

    public String getResponse() { return response; }
    public String getUsedModel() { return usedModel; }
}