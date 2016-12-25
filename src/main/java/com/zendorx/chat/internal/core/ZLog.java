package com.zendorx.chat.internal.core;

/**
 * Created by user on 21.12.2016.
 */
public class ZLog {
    String tag;
    static String RED = "\033[31m";
    static String WHITE = "\033[37m";
    static String END = "\033[0m";
    static String YELLOW = "\033[33m";
    boolean enabled = true;



    public ZLog(String tag)
    {
        this.tag = tag;
    }

    public String toWhite(String text)
    {
        return WHITE + text + END;
    }
    public String toRed(String text)
    {
        return RED + text + END;
    }
    public String toYellow(String text)
    {
        return YELLOW + text + END;
    }


    public void m(String message)
    {
        print(tag + "\t" + message);
    }
    public void w(String message)
    {
        print(toYellow(tag + "\t" + message));
    }
    public void e(String message) { print(toRed(tag + "\t" + message)); }

    void print(String message)
    {
        if (enabled)
            System.out.println(message);
    }
    /*
    System.out.println("\033[0m BLACK");
        System.out.println(" RED");
        System.out.println("\033[32m GREEN");
        System.out.println(" YELLOW");
        System.out.println("\033[34m BLUE");
        System.out.println("\033[35m MAGENTA");
        System.out.println("\033[36m CYAN");
        System.out.println("\033[37m WHITE")
     */

    void disable()
    {
        enabled = false;
    }

    void enable()
    {
        enabled = true;
    }
}
