package com.maslov;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.Thread.currentThread;

public class MyThread implements Runnable {
    private final Socket accept;
    private final List<String> validPath;
    private final BufferedReader in;
    private final BufferedWriter out;

    public MyThread(List<String> validPath, Socket accept) throws IOException {
        this.accept = accept;
        this.validPath = validPath;
        in = new BufferedReader(new InputStreamReader(accept.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        try {
            while (!currentThread().isInterrupted()) {
                String requestLine = in.readLine();
            }
        } catch (IOException e) {

        }
    }
}
