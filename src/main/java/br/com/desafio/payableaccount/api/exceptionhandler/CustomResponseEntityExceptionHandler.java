package br.com.desafio.payableaccount.api.exceptionhandler;

import br.com.desafio.payableaccount.domain.exception.FieldStatusNotFoundException;
import br.com.desafio.payableaccount.domain.exception.PayableAccountNotFoundException;
import br.com.desafio.payableaccount.domain.exception.PayableAccountStatusNotFoundException;
import br.com.desafio.payableaccount.domain.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                problemDetail.setDetail("Campo " + e.getKey() + " não encontrado");

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
                problemDetail.setDetail("Status " + e.getStatus() + " não encontrado");

                return problemDetail;
            }
        };

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PayableAccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePayableAccountNotFoundException(PayableAccountNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.NOT_FOUND;
            }

            @Override
            public ProblemDetail getBody() {

                ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
                problemDetail.setTitle("Conta não encontrada");
                problemDetail.setDetail("Conta não encontrada na base de dados");

                return problemDetail;
            }
        };

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.UNAUTHORIZED;
            }

            @Override
            public ProblemDetail getBody() {

                ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                problemDetail.setTitle("Acesso não autorizado");
                problemDetail.setDetail("Acesso deste usuário não autorizado");

                return problemDetail;
            }
        };

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.CONFLICT;
            }

            @Override
            public ProblemDetail getBody() {

                ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
                problemDetail.setTitle("Usuário já existente");
                problemDetail.setDetail("Usuário já existente, selecione outro login");

                return problemDetail;
            }
        };

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
