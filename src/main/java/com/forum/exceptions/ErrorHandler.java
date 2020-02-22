package com.forum.exceptions;

import com.forum.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDTO> handle(MethodArgumentNotValidException exp) {
        List<ErrorDTO> listDTO = new ArrayList<>();
        List<FieldError> fieldErrors = exp.getBindingResult().getFieldErrors();

        fieldErrors.forEach(x -> {
            String msn = messageSource.getMessage(x, LocaleContextHolder.getLocale());
            ErrorDTO e = new ErrorDTO(x.getField(), msn);
            listDTO.add(e);
        });

        return listDTO;
    }

}
