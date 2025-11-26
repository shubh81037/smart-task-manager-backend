package com.example.taskManager.Advice;

import com.example.taskManager.exception.EmailAlreadyRegistered;
import com.example.taskManager.exception.TaskNotFound;
import com.example.taskManager.model.ErrorDTO;
import com.example.taskManager.model.Task;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;



@Hidden     // This tells SpringDoc to ignore this class during OpenAPI scanning and won't failed during loading of swagger v3-api docs on browser.
@ControllerAdvice
public class    ExceptionsHandler
{


    @ExceptionHandler(EmailAlreadyRegistered.class)
    public ResponseEntity<ErrorDTO> emailAlreadyRegistered(EmailAlreadyRegistered exception)
    {
        ErrorDTO errorDTO = new ErrorDTO() ;

        errorDTO.setErrorMessage(exception.getMessage());
        errorDTO.setStatusCode(HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<ErrorDTO> taskNotFound(TaskNotFound exception)
    {
        ErrorDTO errorDTO = new ErrorDTO() ;

        errorDTO.setErrorMessage(exception.getMessage());
        errorDTO.setStatusCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDTO> expiredToken(ExpiredJwtException exception)
    {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setErrorMessage(exception.getMessage());
        errorDTO.setStatusCode( HttpStatus.UNAUTHORIZED.value() );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> dublicateRoleEntry(SQLIntegrityConstraintViolationException exception)
    {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setErrorMessage(exception.getMessage());
        errorDTO.setStatusCode( HttpStatus.BAD_REQUEST.value() );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDTO> dublicateRoleEntry(AuthorizationDeniedException exception)
    {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setErrorMessage(exception.getMessage());
        errorDTO.setStatusCode( HttpStatus.UNAUTHORIZED.value() );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> inputMisMatch(MethodArgumentTypeMismatchException exception)
    {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setErrorMessage(exception.getMessage());
        errorDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDenied(AccessDeniedException ex)
    {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setErrorMessage(ex.getMessage());
        errorDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);

    }



}
