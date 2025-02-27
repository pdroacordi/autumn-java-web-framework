package io.acordi.autumn.web.http;

public class HttpResponse {

    private int status;
    private String body;

    public HttpResponse() {
        this.status = 200;
        this.body = "";
    }

    public HttpResponse(Builder builder){
        this.status = builder.status;
        this.body = builder.body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private int status;
        private String body;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}
