package by.matthewvirus.model;

public enum DefaultValues {
    OWNER_NAME("БЕЛОРУССКАЯ Ж.Д.") ,CURRENCY_CODE("BYN"), CURRENCY_NAME("Белорусский рубль");
    private final String code;
    DefaultValues (String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}