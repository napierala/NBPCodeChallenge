package pl.napierala.nbpcodechallenge.model;

public enum CurrencyType {
    EUR("EUR"),
    PLN("PLN");

    private String code;

    private CurrencyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CurrencyType findByCodeOrThrowException(String code) {

        for (CurrencyType value : CurrencyType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("CurrenyCode:[" + code + "], is invalid.");
    }
}