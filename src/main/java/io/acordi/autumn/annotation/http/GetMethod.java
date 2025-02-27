package io.acordi.autumn.annotation.http;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetMethod {

    public String value() default "/";

}
