package ma.octo.assignement.web.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.octo.assignement.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandelingController {

    @ExceptionHandler(SoldeDisponibleInsuffisantException.class)
    public ResponseEntity<Object> handleSoldeDisponibleInsuffisantException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(CompteNonExistantException.class)
    public ResponseEntity<Object> handleCompteNonExistantException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(MontantMaxDepasseException.class)
    public ResponseEntity<Object> handleMontantMaxDepasseException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(MontantMinNonAtteintExeption.class)
    public ResponseEntity<Object> handleMontantMinNonAtteintExeption(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(MotifVideException.class)
    public ResponseEntity<Object> handleMotifVideException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(UtilisateurNonExistantException.class)
    public ResponseEntity<Object> handleUtilisateurNonExistantException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleTransactionException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionData exceptionData = new ExceptionData(exception.getMessage(),httpStatus);
        return new ResponseEntity<>(exceptionData, httpStatus);
    }
}
@Data @NoArgsConstructor @AllArgsConstructor
class ExceptionData{
    private  String message;
    private  HttpStatus httpStatus;
}
