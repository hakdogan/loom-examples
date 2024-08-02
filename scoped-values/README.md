# Scoped Values

The Scoped Values API allows us to store and share immutable data for a bounded lifetime. The example in this module shows this facility with a concrete example.

<details>
<summary>org.jugistanbul.handler.RequestHandler.java</summary>

[This example](https://github.com/hakdogan/loom-examples/blob/main/scoped-values/src/main/java/org/jugistanbul/handler/RequestHandler.java) shows how to share immutable data safely and efficiently for a bounded lifetime by using one-way data transfer between components.

```java
//RequestHandler.java
ScopedValue.where(PRINCIPAL, authority).run(() -> {
    var access = Database.access();
    ...
});

//Database.java
public static boolean access(){
    var authority = PRINCIPAL.get();
    return "admin".equals(authority.username());
}
```

```shell
http :8080 username==admin password==12345

HTTP/1.1 200 OK
Content-Type: text/plain

Permission: true
```
</details>