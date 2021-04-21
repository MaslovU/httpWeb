package com.maslov;

import com.maslov.server.Request;
import com.maslov.server.Server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        server.addHandler("GET", "/public/index.html", Main::sendResponse);
        server.addHandler("GET", "/public/spring.png", Main::sendResponse);
//        server.addHandler("GET", "/spring.png", Main::sendResponse);
//        server.addHandler("POST", "/messages", (request, responseStream) -> {
//            sendResponse(request, responseStream);
//        });

        server.listen(9999);
    }

    public static void sendResponse(Request request, BufferedOutputStream responseStream) {
        final var filePath = Path.of("src", "public", request.getPath());
        final String mimeType;
        try {
            mimeType = Files.probeContentType(filePath);
            final var length = Files.size(filePath);
            responseStream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + mimeType + "\r\n" +
                    "Content-Length: " + length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n"
            ).getBytes());
            Files.copy(filePath, responseStream);
            responseStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
