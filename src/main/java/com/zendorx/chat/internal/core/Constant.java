package com.zendorx.chat.internal.core;

/**
 * Created by user on 31.10.2016.
 */
public class Constant {

    public static String CONNECTION_PREFIX_ID = "con_handler_";
    public static String MANAGER_ID = "manager_id";


    public static String getConnectionHandlerID(int id)
    {
        return CONNECTION_PREFIX_ID + String.valueOf(id);
    }
}
