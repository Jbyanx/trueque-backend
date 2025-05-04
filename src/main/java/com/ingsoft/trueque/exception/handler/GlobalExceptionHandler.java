package com.ingsoft.trueque.exception.handler;

import com.ingsoft.trueque.dto.response.ApiError;
import com.ingsoft.trueque.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ImagenNoValidaException.class)
    public ResponseEntity<ApiError> handleImagenNoValidaException(ImagenNoValidaException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.BAD_REQUEST,
                        "la imagen enviada no se guardó correctamente",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(AccesoNoPermitidoException.class)
    public ResponseEntity<ApiError> handleAccesoNoPermitidoException(AccesoNoPermitidoException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.BAD_REQUEST,
                        "El usuario no tiene permitido realizar esta accion",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(AdministradorNotFoundException.class)
    public ResponseEntity<ApiError> handleAdministradorNotFoundException(AdministradorNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ArticuloNotFoundException.class)
    public ResponseEntity<ApiError> handleArticuloNotFoundException(ArticuloNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<ApiError> handleArticuloNotFoundException(CategoriaNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(IntercambioNotFoundException.class)
    public ResponseEntity<ApiError> handleIntercambioNotFoundException(IntercambioNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(LogicaNegocioException.class)
    public ResponseEntity<ApiError> handleLogicaNegocioException(LogicaNegocioException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(PersonaNotFoundException.class)
    public ResponseEntity<ApiError> handlePersonaNotFoundException(PersonaNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(ReporteNotFoundException.class)
    public ResponseEntity<ApiError> handleReporteNotFoundException(ReporteNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(ResenhaNotFoundException.class)
    public ResponseEntity<ApiError> handleResenhaNotFoundException(ResenhaNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ApiError> handleUsuarioNotFoundException(UsuarioNotFoundException e){
        return ResponseEntity.badRequest().body(
                new  ApiError(
                        HttpStatus.NOT_FOUND,
                        e.getMessage(),
                        e.getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException e) {
        String errorDetails = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        "Error de validación",
                        errorDetails,
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        "Restricción de validación fallida",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(
                new ApiError(
                        HttpStatus.PAYLOAD_TOO_LARGE,
                        "Archivo demasiado grande",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest().body(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        "Falta un parámetro en la solicitud",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableMessage(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        "Cuerpo del request malformado",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ApiError(
                        HttpStatus.METHOD_NOT_ALLOWED,
                        "Método HTTP no soportado",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiError> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
                new ApiError(
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        "Tipo de contenido no soportado",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        "Argumento inválido",
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleGenericException(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                new ApiError(
//                        HttpStatus.INTERNAL_SERVER_ERROR,
//                        "Error interno del servidor",
//                        e.getMessage(),
//                        LocalDateTime.now()
//                )
//        );
//    }
}
