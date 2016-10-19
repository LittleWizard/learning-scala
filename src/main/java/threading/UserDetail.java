package threading;


public class UserDetail {

    private final String identifier;

    private final String name;

    public UserDetail(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}