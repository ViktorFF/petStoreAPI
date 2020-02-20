package by.rest.petstore.controller.exception;

import by.rest.petstore.exception.pet.PetNotFoundException;
import by.rest.petstore.model.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class PetExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(400, "Client Error", "Pet not valid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ApiResponse> petNotFound() {
        return new ResponseEntity<>(new ApiResponse(404, "Client Error", "Pet not found"), HttpStatus.NOT_FOUND);
    }
}
