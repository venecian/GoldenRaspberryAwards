package br.com.filmes.exceptions;

public class MovieException extends RuntimeException {

    private final int statusCode;

    public MovieException(String mensagem){
        super(mensagem);
        this.statusCode = 400;
    }

    public MovieException(String mensagem, int statusCode){
        super(mensagem);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
