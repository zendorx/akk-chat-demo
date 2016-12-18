package com.testchat.trash;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by user on 25.10.2016.
 */
public class ChatClient extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);


    public static void main(String[] args)
    {

    }

    @Override
    public void onReceive(Object message) throws Throwable {

    }
}
