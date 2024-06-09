package br.com.contas.infrastructure.adapters.input.rest.exception;

import br.com.contas.infrastructure.adapters.output.persitence.exception.FieldStatusNotFoundException;
import br.com.contas.infrastructure.adapters.output.persitence.exception.PayableAccountStatusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFieldStatusNotFoundException(FieldStatusNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.NOT_FOUND;
            }

            @Override
            public ProblemDetail getBody() {

                ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
                problemDetail.setTitle("Campo não encontrado");
                problemDetail.setDetail("Campo " + e.getKey() + " não encontrado ");

                return problemDetail;
            }
        };

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PayableAccountStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePayableAccountStatusNotFoundException(PayableAccountStatusNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.NOT_FOUND;
            }

            @Override
            public ProblemDetail getBody() {

                ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
                problemDetail.setTitle("Status não encontrado");
                problemDetail.setDetail("Status " + e.getStatus() + " não encontrado ");

                return problemDetail;
            }
        };

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
