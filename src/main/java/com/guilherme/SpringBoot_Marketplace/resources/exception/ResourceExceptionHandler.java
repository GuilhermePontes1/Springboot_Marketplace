package com.guilherme.SpringBoot_Marketplace.resources.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.guilherme.SpringBoot_Marketplace.services.exception.AuthorizationException;
import com.guilherme.SpringBoot_Marketplace.services.exception.DataIntegrityException;
import com.guilherme.SpringBoot_Marketplace.services.exception.FileException;
import com.guilherme.SpringBoot_Marketplace.services.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

// classe auxiliar que vai interceptar as exceções 
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class) // para indicar que é uma exceção desse tipo de exceção
    public ResponseEntity<StandardError> objectNotFoud(ObjectNotFoundException e, HttpServletRequest request) {
        // em cima é padrão da anotação ControllerAdvice
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        // em cima estamos instanciando o erro que irá retornar o notFound no caso 404,
        // o id que procura além do
        // pacote e o tempo de execução

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> DataIntegrity(DataIntegrityException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // aqui digitamos os tipos de erros que queremos que apareçam
        // para o usuário
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());

        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage()); // procura os erros dentro do método "MethodArgumentNotValidException" adiciona eles a lista que irá ser mostrada
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AuthorizationException.class) // para indicar que é uma exceção desse tipo de exceção
    public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
        // em cima é padrão da anotação ControllerAdvice
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        // em cima estamos instanciando o erro que irá retornar o notFound no caso FORBIDDEN OU acesso negado,
        // o id que procura além do
        // pacote e o tempo de execução

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(FileException.class) // para indicar que é uma exceção desse tipo de exceção
    public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
        // Erro personalizado na execução de envio de arquivos enviados para o amazon AWS
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonServiceException.class) // para indicar que é uma exceção desse tipo de exceção
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {


        HttpStatus code = HttpStatus.valueOf(e.getErrorCode()); // pega o códigoo http que veio da exceção e transforma para objeto http do tipo status para mostrar o erro ao usuárrio
        StandardError err = new StandardError(code.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(code).body(err);

    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
   // Classes para gerar erros personalizados em geral
}