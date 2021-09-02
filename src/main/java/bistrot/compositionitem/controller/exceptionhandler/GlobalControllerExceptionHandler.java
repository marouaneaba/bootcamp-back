package bistrot.compositionitem.controller.exceptionhandler;

import bistrot.compositionitem.exception.CompositionItemNameIsBlankException;
import bistrot.compositionitem.exception.CompositionItemNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(CompositionItemNameIsBlankException.class)
    public ResponseEntity handleStockCapacity(CompositionItemNameIsBlankException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CompositionItemNotFoundException.class)
    public ResponseEntity handleStockCapacity(CompositionItemNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
