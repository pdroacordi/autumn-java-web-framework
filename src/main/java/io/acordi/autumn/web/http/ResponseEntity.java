package io.acordi.autumn.web.http;

public class ResponseEntity<T> {

    private final int status;
    private final T body;

    private ResponseEntity(int status, T body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public T getBody() {
        return body;
    }


    public static<T> ResponseEntity<T> ok(T t) {
        return new ResponseEntity<>(200, t);
    }

    public static<T> ResponseEntity<T> created(T t) {
        return new ResponseEntity<>(201, t);
    }

    public static<T> ResponseEntity<T> notFound(T t) {
        return new ResponseEntity<>(404, t);
    }

    public static<T> ResponseEntity<T> internalServerError(T t) {
        return new ResponseEntity<>(500, t);
    }

    public static<T> ResponseEntity<T> withStatus(int status, T t) {
        return new ResponseEntity<>(status, t);
    }


}
