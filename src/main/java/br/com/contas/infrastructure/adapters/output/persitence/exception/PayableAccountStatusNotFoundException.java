package br.com.contas.infrastructure.adapters.output.persitence.exception;

public class PayableAccountStatusNotFoundException extends RuntimeException {

    private final String status;

    public PayableAccountStatusNotFoundException(String status) {
        super("Status não encontrado: " + status);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
