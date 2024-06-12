package br.com.desafio.payableaccount.domain.exception;

public class FieldStatusNotFoundException extends RuntimeException {

    private final String key;

    public FieldStatusNotFoundException(String key) {
        super("Campo não encontrado: " + key);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
