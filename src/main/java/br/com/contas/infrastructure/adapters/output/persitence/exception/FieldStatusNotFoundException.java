package br.com.contas.infrastructure.adapters.output.persitence.exception;

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
