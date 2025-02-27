package io.acordi.autumn.web.routing;

import java.lang.reflect.Method;

public class Route {
    private final String method;
    private final String path;
    private final String body;
    private final Class<?> controller;
    private final Method controllerMethod;

    public Route(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.body = builder.body;
        this.controller = builder.controller;
        this.controllerMethod = builder.controllerMethod;
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

    public Class<?> getController() {
        return controller;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public static class Builder {
        private String method;
        private String path;
        private String body;
        private Class<?> controller;
        private Method controllerMethod;

        public Builder method(String method) {
            this.method = method;
            return this;
        }
        public Builder path(String path) {
            this.path = path;
            return this;
        }
        public Builder body(String body) {
            this.body = body;
            return this;
        }
        public Builder controller(Class<?> controller) {
            this.controller = controller;
            return this;
        }
        public Builder controllerMethod(Method controllerMethod) {
            this.controllerMethod = controllerMethod;
            return this;
        }
        public Route build() {
            return new Route(this);
        }
    }
}
