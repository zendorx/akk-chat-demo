package com.zendorx.chat.internal.core;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by user on 18.12.2016.
 */
public class Api {
    public static String REGISTER = "register";
    public static String LOGIN = "login";
    public static String MESSAGE = "message";
    public static String CREATE_ROOM = "create_room";
    public static String DELETE_ROOM = "delete_room";
    public static String ADD_TO_ROOM = "add_to_room";
    public static String REMOVE_FROM_ROOM = "remove_from_room";

    public static String ID = "uid";
    public static String PASSWORD = "password";
    public static String TEXT = "text";
    public static String ROOM = "room";


    private static final Map<String, List<String>> command_params = new HashMap<String, List<String>>() {{
        put(LOGIN, Arrays.asList(ID, PASSWORD));
        put(REGISTER, Arrays.asList(ID, PASSWORD));
        put(MESSAGE, Arrays.asList(ROOM, TEXT));
        put(CREATE_ROOM, Arrays.asList(ROOM));
        put(DELETE_ROOM, Arrays.asList(ROOM));
        put(ADD_TO_ROOM, Arrays.asList(ROOM, ID));
        put(REMOVE_FROM_ROOM, Arrays.asList(ROOM, ID));
    }};

    private static final Map<String, String> command_classes = new HashMap<String, String>() {{
        put(LOGIN, Command.Login.class.getName());
        put(REGISTER, Command.Register.class.getName());
        put(MESSAGE, Command.Message.class.getName());
    }};

    private static ZLog zlog = new ZLog("Api");


    private static String makeRequest(String id, String... args)
    {
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        for (int i = 0; i < args.length-1; ++i) {
            String param = args[i];
            String value = args[i+1];
            data.put(param, value);
        }

        return obj.put(id, data).toString();
    }

    public static String login(String id, String password)
    {
        return makeRequest(LOGIN, ID, id, PASSWORD, password);
    }
    public static String register(String id, String password)
    {
        return makeRequest(REGISTER, ID, id, PASSWORD, password);
    }

    public static String message(String room, String text)
    {
        return makeRequest(MESSAGE, ROOM, room, TEXT, text);
    }
    public static String createRoom(String id)
    {
        return makeRequest(CREATE_ROOM, ROOM, id);
    }
    public static String deleteRoom(String id)
    {
        return makeRequest(DELETE_ROOM, ROOM, id);
    }
    public static String addToRoom(String roomID, String userID)
    {
        return makeRequest(ADD_TO_ROOM, ROOM, roomID, ID, userID);
    }

    public static String removeFromRoom(String roomID, String userID)
    {
        return makeRequest(REMOVE_FROM_ROOM, ROOM, roomID, ID, userID);
    }




    public static List<Object> makeCommands(String request)
    {
        List<Object> result = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONObject obj = null;

        try {
            obj = (JSONObject) parser.parse(request);
            Set<Object> keys = obj.keySet();

            for (Object key : keys)
            {
                if (key instanceof String)
                {
                    String id = (String) key;
                    if (command_params.containsKey(id))
                    {
                        Object object = makeCommand(id, (JSONObject) obj.get(key));
                        if (object != null)
                        {
                            result.add(object);
                        }
                        else
                        {
                            zlog.e("Request parse error: null object '" + id + "' in \n" + request);
                        }
                    }
                    else
                    {
                        zlog.e("Request parse error: unknown command id '" + id + "' in \n" + request);
                    }
                }
                else
                {
                    zlog.e("Request parse error: unknown class '" + key.toString() + "' in \n" + request);
                }
            }

            return result;

        } catch (ParseException e) {
            zlog.e("Request parse error: unhandled exception in request \n" + request);
            e.printStackTrace();
        };

        return null;
    }

    public static boolean isValidParams(String id, JSONObject object)
    {
        if (command_params.containsKey(id))
        {
            List<String> params = command_params.get(id);
            for (String p: params)
            {
                if (!object.containsKey(p))
                {
                    zlog.e("Request parameter " + p + " not found in " + id + " " + object);
                    return false;
                }
            }
        }
        return true;
    }

    private static Object makeCommand(String id, JSONObject object)
    {
        if (isValidParams(id, object))
        {
            if (command_classes.containsKey(id))
            {
                try {
                    Class<?> clazz = Class.forName(command_classes.get(id));
                    Constructor<?> ctor = clazz.getConstructor(JSONObject.class);
                    Object result = ctor.newInstance(new Object[] { object });
                    return result;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                zlog.e("makeCommand error: " + id);
            }
            else
            {
                zlog.e("makeCommand error: class name not found: " + id + " in " + object );
            }
        }
        return null;
    }
}
