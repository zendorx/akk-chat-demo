package com.zendorx.chat.trash;


import java.lang.Object;

import com.zendorx.chat.trash.ChatCommand;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


 public class CommandParser {

    public static Object parse(final String command)
    {
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(command);
            String id = (String) object.get("id");
            ChatCommand.Command cmd = null;

            if (id.equals(ChatCommand.ID_REGISTER))
            {
                String name = (String) object.get("name");
                cmd = new ChatCommand.RegisterCommand(name);
            }


            if (cmd == null)
            {
                System.out.println("Parse error: " + command);
            }

            return cmd;

        }catch(Exception pe) {
            return null;
        }
    }

}
