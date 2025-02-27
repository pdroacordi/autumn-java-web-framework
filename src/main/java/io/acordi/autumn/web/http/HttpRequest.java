package io.acordi.autumn.web.http;

public class HttpRequest {

    private final String method;
    private final String path;
    private final String body;

    public HttpRequest(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.body = builder.body;

    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private String method;
        private String path;
        private String body;

        public Builder method(final String method) {
            this.method = method;
            return this;
        }
        public Builder path(final String path) {
            this.path = path;
            return this;
        }
        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
