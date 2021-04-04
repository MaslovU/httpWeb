package com.maslov;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port = 9999;
    private final List<String> validPath = List.of("/index.html", "/spring.svg", "/spring.png");
    private final ExecutorService threadPool = Executors.newFixedThreadPool(64);

    public Server() {
        System.out.println("Server is running...");
        try (ServerSocket serverSocket = new ServerSocket(port);
        ) {
            while (true) {
                threadPool.execute(new MyThread(validPath, serverSocket.accept()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }
    }
}
