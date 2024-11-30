package org.yahid.gears.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //TODO we would read

            //TODO we would write
            String html = "<html><head>Gears</head><body><h1>This Page is coming from my Java server gears!</h1></body></html>";

            /*
             * after status line we need two chars the carriage return and line feed => '\n\r'
             */

            String CRLF = "\n\r";
            //response
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // syntax -> Status Line -> HTTP/VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF +//header
                            CRLF +
                            html + // data (payload)
                            CRLF + CRLF;

            outputStream.write(response.getBytes()); // send output in bytes;

            LOGGER.info("process finished");
        } catch (Exception e){
            LOGGER.error("Problem with communication:", e);
        } finally {
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
