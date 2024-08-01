# Scoped Values

The Scoped Values API allows us to store and share immutable data for a bounded lifetime and only the thread that wrote the data can read it.

The example in this module shows this facility with a concrete example.

```java
ScopedValue.where(PRINCIPAL, authority).run(() -> {
    var access = Database.access();
    var msg = access ? "HTTP/1.1 200 OK\r\n" : "HTTP/1.1 401 Unauthorized Access\r\n";
    response
            .append(msg)
            .append("Content-Type: text/plain\r\n\n")
            .append("Permission: ")
            .append(access);
});
```

```shell
http :8080 username==admin password==12345

HTTP/1.1 200 OK
Content-Type: text/plain

Permission: true
```