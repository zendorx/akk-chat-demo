package com.zendorx.chat.main;


import com.zendorx.chat.Server;

import java.util.concurrent.TimeUnit;


public class StartTest {

    static String url = "localhost";
    static int port = 9090;

    public static void main(String[] args)
    {
        Runnable serverTask = runServer();

        wait(3);

        firstUser();

        wait(serverTask);
    }



    static Runnable runServer()
    {
        Thread task = new Thread() {
            @Override
            public void run() {
                Server.start(url, port);
            }
        };
        task.start();
        return task;
    }

    static void firstUser()
    {
        ClientBot bot = new ClientBot(url, port, "firstUser");
    }

    static void wait(Runnable task)
    {
        try {
            task.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static void wait(int sec)
    {
        try
        {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
