package br.com.desafio.payableaccount.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;

public record UserInput(@NotBlank String username,
                        @NotBlank String password) {
}
