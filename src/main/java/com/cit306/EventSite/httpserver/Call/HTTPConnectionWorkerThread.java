package com.cit306.EventSite.httpserver.Call;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HTTPConnectionWorkerThread.class);

    private final Socket socket;

    public HTTPConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my simple - but not so simple - HTTP Server written in Java.</h1></body></html>";

            final String CRLF = "\n\r"; //13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF +  // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info("Connection processing finished{}", socket.getInetAddress());
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null ) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
