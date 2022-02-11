import java.util.Arrays;
public enum EnergyType {
    RENEWABLE("renewable"),
    OTHER("other");

    private String value;
    EnergyType(String type) {
        this.value = type;
    }
    public String getValue() {
        return this.value;
    }
    public static EnergyType get(String value) {
        return Arrays.stream(EnergyType.values())
                .filter(type -> type.value.equals(value))
                .findFirst().get();
    }
}