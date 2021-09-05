package bistrot.web.api.exceptionhandler;


import bistrot.common.error.model.ApiError;
import bistrot.common.exception.CompositionItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(CompositionItemNotFoundException.class)
    public ResponseEntity handleStockCapacity(CompositionItemNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBadHeaderParams(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        if (!errors.isEmpty()) {
            ApiError apiError = ApiError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(errors.stream().findFirst().map(error -> error.getDefaultMessage()).orElse(null))
            .build();
            return ResponseEntity.status(apiError.getStatus())
                    .body(apiError);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
    }

}
