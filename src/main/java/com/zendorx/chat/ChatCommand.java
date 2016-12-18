package com.zendorx.chat;

import java.io.Serializable;

public class ChatCommand {

    public static String ID_REGISTER = "register";
    public static String ID_LOGIN = "login";
    public static String ID_CREATE_ROOM = "create_room";
    public static String ID_LEAVE_ROOM = "leave_room";
    public static String ID_MESSAGE = "message";


    public String id;
    public String data;

    public static class Command implements Serializable {
        public String id;
    }

    public static class RegisterCommand extends Command
    {
        String name;
        RegisterCommand(final String name)
        {
            this.id = ID_REGISTER;
            this.name = name;
        }
    }

}
