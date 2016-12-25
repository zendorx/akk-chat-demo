package com.zendorx.chat.internal;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.Serializable;

/**
 * Created by user on 31.10.2016.
 */
public class ConnectionManager /*extends UntypedActor*/ {

    /*private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static class Register implements Serializable {
        public final int internalID;

        public Register(int internalID) {
            this.internalID = internalID;
        }


    }

    public static class Unregister implements Serializable {
        public final int internalID;

        public Unregister(int internalID) {
            this.internalID = internalID;
        }
    }

    public static Props props() {
        return Props.create(ConnectionManager.class);
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof Register) {
            Register reg = (Register) message;
            log.info("ConnectionManager: client registered " + String.valueOf(reg.internalID));
        } else if (message instanceof Unregister) {
            Unregister reg = (Unregister) message;
            log.info("ConnectionManager: client unregistered " + String.valueOf(reg.internalID));
        }
    }*/
}