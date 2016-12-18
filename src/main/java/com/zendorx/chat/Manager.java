package com.zendorx.chat;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.Serializable;

/**
 * Created by user on 31.10.2016.
 */
public class Manager extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static class Register implements Serializable
    {
        public final int internalID;

        public Register(int internalID)
        {
            this.internalID = internalID;
        }


    }

    public static class Unregister implements Serializable
    {
        public final int internalID;

        public Unregister(int internalID)
        {
            this.internalID = internalID;
        }
    }

    /*
    * list of users
    * rooms
    *
    * actions:
    *   add user
    *   remove user
    *   set room
    *   leave room
    *   message to room
    * */

    public  static Props props() {
        return Props.create(Manager.class);
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof Register)
        {
            Register reg = (Register) message;
            log.info("Manager: client registered " + String.valueOf(reg.internalID));
        }
        else
        if (message instanceof Unregister)
        {
            Unregister reg = (Unregister) message;
            log.info("Manager: client unregistered " + String.valueOf(reg.internalID));
        }
    }

    /* internal functions */
    void addUser(int id)
    {

    }

    boolean isUserExists(int id)
    {
        return false;
    }

    void removeUser(int id)
    {

    }

    void addRoom(final String roomID, int id)
    {

    }

    void removeRoom(final String roomID, int id)
    {

    }

    void messageRoom(final String roomID, final String message)
    {

    }
}
/*
*
*  Новый пользователь присылает комманду регистрации
*   Записывается в базу по логину и паролю
*
*
*
*
*
*
*
*
* */