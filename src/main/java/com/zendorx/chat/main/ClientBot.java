package com.zendorx.chat.main;

import java.io.IOException;
import java.net.Socket;
import com.zendorx.chat.internal.core.Api;

public class ClientBot {

    Socket socket;
    String nickname;
    String password = "easypass";


    public ClientBot(String url, int port, String nickname)
    {
        this.nickname = nickname;

        try
        {
            log("Init");
            socket = new Socket(url, port);
            register();
            String answer = readData();
            System.out.println("Server answer: " + answer);
        }
        catch(Exception e)
        {
            System.out.println("ClientBot init error: " + e);
        }
    }

    void register() {
        sendData(Api.register(nickname, password));
    }
    void disconnect() {
    }
    void login() {


    }

    void log(String message)
    {
        System.out.println("ClientBot[" + nickname + "]: " + message);
    }

    String readData() {
        try {
            byte buf[] = new byte[64*1024];
            int r = socket.getInputStream().read(buf);
            String data = new String(buf, 0, r);
            log("readData " + data);
            return data;
        } catch (IOException e) {
            log("readData exception");
            e.printStackTrace();
        }
        return "";
    }

    void sendData(String data) {
        log("sendData " + data);
        try {
            socket.getOutputStream().write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void makeDecision(String data)
    {

    }
}
