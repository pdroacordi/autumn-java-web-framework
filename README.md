# 🍂 Autumn Web Framework 🍂

---

Autumn Web Framework is a lightweight and educational web framework for Java, inspired by Spring Boot. It provides essential features like dependency injection, REST controllers, and JSON object mapping. The framework is designed to help developers understand the internals of a web framework while building real applications. 🚀



## ✨ Features

- 🛠 **Routing:** Supports `@GetMethod` and `@PostMethod` for handling HTTP requests.
- 💉 **Dependency Injection:** Provides a simple inversion of control (IoC) mechanism.
- 🌍 **REST Controllers:** Enables easy creation of REST APIs with annotated controllers.
- 📦 **JSON Support:** Handles request bodies and responses in JSON format.
- 🔥 **Embedded Server:** Runs on an embedded Tomcat instance.


---
## 🚀 Getting Started

### 🏃 Running an Application

To use Autumn Web Framework, you need to call the `run` method of `AutumnWebApplication` in your main class.

#### 1️⃣ Create a Main Class

```java
import io.acordi.autumn.server.AutumnWebApplication;

public class MyApplication {
    public static void main(String[] args) {
        AutumnWebApplication.run(MyApplication.class);
    }
}
```

#### 2️⃣ (Optional) Create a Service Interface and Implementation
```java
public interface TestService {
    String saySomething();
}
```
```java
public class TestServiceImplementation implements TestService {
    @Override
    public String saySomething(){
        return "Dependency Injection rules!";
    }
}
```

#### 3️⃣ Create a REST Controller

```java
import io.acordi.autumn.annotation.Injected;
import io.acordi.autumn.annotation.http.GetMethod;
import io.acordi.autumn.annotation.http.PostMethod;
import io.acordi.autumn.annotation.http.RequestBody;
import io.acordi.autumn.annotation.http.RestController;
import io.acordi.autumn.web.http.ResponseEntity;

@RestController
public class HelloController {

    @Injected
    private TestService service;

    @GetMethod("/hello")
    public String sayHello() {
        return "Hello, Autumn Web!";
    }

    @PostMethod("/echo")
    public String echo(@RequestBody String body) {
        return "Received: " + body;
    }

    @GetMethod("/injection")
    public ResponseEntity<String> injection(){
        return ResponseEntity.ok(service.saySomething());
    }
}
```

#### 4️⃣ Run the Application

Run your `MyApplication` class, and the server will start automatically, making your REST endpoints available.

---

## 🔮 Future Work

The Autumn Web Framework is still in development, and the following features are planned:

-  **More HTTP Methods:** Support for PUT, DELETE, PATCH.
-  **Configuration Management:** Externalized properties and environment variables.
-  **Parameter Handling:** Add support for query parameters (`?key=value`) and header parameters.

---

## 🤝 Contributions

This project is meant for educational purposes. Feel free to fork, contribute, or suggest improvements! ✍️

---

## 📜 License

📝 MIT License.