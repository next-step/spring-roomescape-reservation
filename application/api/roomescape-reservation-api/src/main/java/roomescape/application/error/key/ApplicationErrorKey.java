package roomescape.application.error.key;

public class ApplicationErrorKey {

    private static final String MESSAGE_FORMAT = "[%s : %s] ";

    private final String name;
    private final String value;

    public ApplicationErrorKey(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toMessage() {
        return String.format(MESSAGE_FORMAT, this.name, this.value);
    }
}
