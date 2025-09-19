package domain;

public class UserQuery {
    private final String text;

    public UserQuery(String text) {
        this.text = text == null ? "" : text.trim();
    }

    public String getText() {
        return text;
    }
}