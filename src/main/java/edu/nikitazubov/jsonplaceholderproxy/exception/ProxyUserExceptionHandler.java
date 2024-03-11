package edu.nikitazubov.jsonplaceholderproxy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProxyUserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectRequestData> handleException(
            IncorrectRequestException exception) {
        IncorrectRequestData data = new IncorrectRequestData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
