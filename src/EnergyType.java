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
}