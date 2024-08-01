package org.jugistanbul.handler;

import org.jugistanbul.data.Database;
import org.jugistanbul.entity.Authorization;
import org.jugistanbul.util.CustomException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 1.08.2024
 ***/
public class RequestHandler {

    public static ScopedValue<Authorization> PRINCIPAL = ScopedValue.newInstance();

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {

        try (var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             var out = clientSocket.getOutputStream()) {

            String line = in.readLine();
            if (line == null || line.isEmpty()) {
                return;
            }

            var authority = createAuthorityEntity(line);
            var response = new StringBuffer();

            ScopedValue.where(PRINCIPAL, authority).run(() -> {
                var access = Database.access();
                var msg = access ? "HTTP/1.1 200 OK\r\n" : "HTTP/1.1 401 Unauthorized Access\r\n";
                response
                        .append(msg)
                        .append("Content-Type: text/plain\r\n\n")
                        .append("Permission: ")
                        .append(access);
            });

            out.write(response.toString().getBytes());

        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    private Map<String, String> parseQueryParams(final String query) {

        Map<String, String> queryParams = new HashMap<>();
        Arrays.stream(query.split("&")).forEach(pair -> {
            String[] keyValue = pair.split("=");
            var value = keyValue.length > 1
                    ? keyValue[1]
                    : "";
            queryParams.put(keyValue[0], value);
        });

        return queryParams;
    }

    private Authorization createAuthorityEntity(final String line) throws NoSuchAlgorithmException {

        String[] requestLine = line.split(" ");
        var path = requestLine[1];

        String[] pathParts = path.split("\\?");
        var query = pathParts.length > 1 ? pathParts[1] : "";

        Map<String, String> queryParams = parseQueryParams(query);
        var username = Optional.ofNullable(queryParams.get("username")).orElse("");
        var password = Optional.ofNullable(queryParams.get("password")).orElse("");

        var digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return new Authorization(username, bytesToHex(encodedHash));
    }

    private String bytesToHex(final byte[] hash) {
        var hexString = new StringBuilder(2 * hash.length);
        IntStream.range(0, hash.length)
                .forEach(i -> {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    var str = hex.length() == 1 ? "0" + hex : hex;
                    hexString.append(str);
                });

        return hexString.toString();
    }

}
