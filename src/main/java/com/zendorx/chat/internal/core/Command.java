package com.zendorx.chat.internal.core;

import java.io.Serializable;

/**
 * Created by user on 25.12.2016.
 */
public class Command {

    public static Object parseRequest(String request)
    {


        return null;
    }

    public class Login implements Serializable
    {
        String username;
        String password;
    }

    public class Register implements Serializable
    {
        String username;
        String password;
    }

    public class Message implements Serializable
    {
        String room;
        String message;
    }
}
