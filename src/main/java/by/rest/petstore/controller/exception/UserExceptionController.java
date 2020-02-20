package by.rest.petstore.controller.exception;

import by.rest.petstore.exception.user.AuthenticationUserException;
import by.rest.petstore.exception.user.DeleteUserException;
import by.rest.petstore.exception.user.LoginUserException;
import by.rest.petstore.exception.user.UserNotFoundException;
import by.rest.petstore.model.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(400, "Client Error", "User not valid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFound() {
        return new ResponseEntity<>(new ApiResponse(404, "Client Error", "User not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationUserException.class)
    public ResponseEntity<ApiResponse> authEx() {
        return new ResponseEntity<>(new ApiResponse(400, "Client Error", "Invalid username/password supplied"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginUserException.class)
    public ResponseEntity<ApiResponse> userLoginEx() {
        return new ResponseEntity<>(new ApiResponse(400, "Client Error", "User is not logged in"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteUserException.class)
    public ResponseEntity<ApiResponse> userDeleteEx() {
        return new ResponseEntity<>(new ApiResponse(400, "Client Error", "Cannot delete user. User is logged in now"), HttpStatus.BAD_REQUEST);
    }
}
