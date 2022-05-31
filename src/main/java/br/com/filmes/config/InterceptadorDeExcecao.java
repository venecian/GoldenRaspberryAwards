package br.com.filmes.config;

import br.com.filmes.dto.DetalheErroDTO;
import br.com.filmes.dto.ErroDTO;
import br.com.filmes.exceptions.MovieException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class InterceptadorDeExcecao {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> erroDeValidacao(MethodArgumentNotValidException exception) {
        BindingResult erro = exception.getBindingResult();

        List<DetalheErroDTO> erros = new ArrayList<>();
        for (ObjectError allError : erro.getAllErrors()) {
            FieldError fieldError = (FieldError) allError;
            String nomeCampo = fieldError.getField();
            String mensagem = allError.getDefaultMessage();

            DetalheErroDTO detalheErroDTO = new DetalheErroDTO();
            detalheErroDTO.setCampo(nomeCampo);
            detalheErroDTO.setMensagem(mensagem);
            if (fieldError.getRejectedValue() != null) {
                detalheErroDTO.setValorInvalido(fieldError.getRejectedValue().toString());
            }

            erros.add(detalheErroDTO);
        }

        ErroDTO erroDTO = new ErroDTO();
        erroDTO.setErros(erros);
        erroDTO.setStatusCode(400);

        return new ResponseEntity<>(erroDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErroDTO> erroDataIntegrityViolationException(DataIntegrityViolationException exception) {

        ConstraintViolationException psqlException = (ConstraintViolationException) exception.getCause();
        String mensagem = psqlException.getSQLException().getMessage();

        List<DetalheErroDTO> detalheErroDTOS = new ArrayList<>();
        DetalheErroDTO detalheErroDTO = new DetalheErroDTO();
        detalheErroDTO.setMensagem(mensagem);
        detalheErroDTOS.add(detalheErroDTO);

        ErroDTO erroDTO = new ErroDTO();
        erroDTO.setStatusCode(400);
        erroDTO.setErros(detalheErroDTOS);

        return new ResponseEntity<>(erroDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErroDTO> erroGeral(RuntimeException exception) {
        ErroDTO erroDTO = new ErroDTO();

        List<DetalheErroDTO> detalheErroDTOS = new ArrayList<>();
        DetalheErroDTO detalheErroDTO = new DetalheErroDTO();
        detalheErroDTO.setMensagem(exception.getMessage());
        detalheErroDTOS.add(detalheErroDTO);

        erroDTO.setErros(detalheErroDTOS);

        if (exception instanceof MovieException) {
            erroDTO.setStatusCode(((MovieException) exception).getStatusCode());
        } else {
            erroDTO.setStatusCode(500);
        }

        if (erroDTO.getStatusCode() >= 500) {
            return new ResponseEntity<>(erroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(erroDTO, HttpStatus.BAD_REQUEST);
        }
    }


}
