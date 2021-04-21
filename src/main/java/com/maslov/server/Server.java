package com.maslov.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final List<String> validPath = List.of("/index.html", "/classic.html", "/spring.png");
    private final ExecutorService threadPool = Executors.newFixedThreadPool(64);
    private final Map<String, Map<String, Handler>> handlers;
    private final Map<String, Handler> handlerMap = new HashMap<>();

    public Server() {
        System.out.println("Server is running...");
        this.handlers = new HashMap<>();
    }

    public void listen(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                threadPool.execute(new MyThread(validPath, serverSocket.accept(), handlers));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        handlerMap.put(path, handler);
        handlers.put(method, handlerMap);
    }
}
