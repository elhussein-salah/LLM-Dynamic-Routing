package domain;

public class ResultDTO {

    private final String answer;
    private final String usedModel;
    private final long elapsedMs;
    private final boolean fromCache;

    public ResultDTO(String answer, String usedModel, long elapsedMs, boolean fromCache) {
        this.answer = answer;
        this.usedModel = usedModel;
        this.elapsedMs = elapsedMs;
        this.fromCache = fromCache;
    }


    @Override
    public String toString() {
        return "ResultDTO{" +
                "answer='" + answer + '\'' +
                ", usedModel='" + usedModel + '\'' +
                ", elapsedMs=" + elapsedMs +
                ", fromCache=" + fromCache +
                '}';
    }
}
