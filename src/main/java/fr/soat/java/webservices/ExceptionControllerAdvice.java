package fr.soat.java.webservices;

import fr.soat.java.enums.Status;
import fr.soat.java.exceptions.OrderNotFoundException;
import fr.soat.java.exceptions.TooManyProductsException;
import fr.soat.java.payload.wrappers.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(TooManyProductsException.class)
    @ResponseBody
    public ResponseWrapper<String> handleBusinessException(TooManyProductsException exception) {
        return handleExceptionObject(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    public ResponseWrapper<String> handleOrderNotFoundException(OrderNotFoundException exception) {
        return handleExceptionObject(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseWrapper<String> handleGenericException(Exception exception) {
        return handleExceptionObject(exception);
    }

    private ResponseWrapper<String> handleExceptionObject(Exception e) {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        response.setStatus(Status.ERROR);
        response.setData(e.getMessage());
        return response;
    }
}
