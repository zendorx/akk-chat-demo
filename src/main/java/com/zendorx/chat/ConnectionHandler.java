package com.zendorx.chat;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import com.zendorx.simple.TestTCPClient;

import java.net.InetSocketAddress;

public class ConnectionHandler extends UntypedActor {

    private final int internal_id;
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(int id) {
        return Props.create(ConnectionHandler.class, id);
    }

    public ConnectionHandler(int id)
    {
        internal_id = id;
    }

    private ActorRef manager = null;

    private void die()
    {
        getContext().stop(getSelf());
        getSelf().tell(PoisonPill.getInstance(), getSelf());
    }


    @Override
    public void postStop()
    {
        log.info("ConnectionHandler stopped, id: " + String.valueOf(internal_id));

        if (manager != null)
        {
            manager.tell(new Manager.Unregister(internal_id), getSelf());
        }
    }

    @Override
    public void preStart()
    {
        log.info("ConnectionHandler created, id: " + String.valueOf(internal_id));

    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof Tcp.Received)
        {
            log.info("ConnectionHandler Received");
            final String data = ((Tcp.Received) message).data().utf8String();
            Object object = CommandParser.parse(data);

            if (object != null)
            {
               // worker.tell(object, getSelf());
                manager = getContext().actorFor("../" + Constant.MANAGER_ID);
                manager.tell(new Manager.Register(internal_id), getSelf());
            }
            else
            {
                log.info("ConnectionHandler command parse error");
                die();
            }

            /*log.info("In SimplisticHandlerActor - Received message: " + data);
            getSender().tell(TcpMessage.write(ByteString.fromArray(("echo " + data).getBytes())), getSelf());*/

        } else
        if (message instanceof Tcp.ConnectionClosed) {

            log.info("ConnectionHandler " + String.valueOf(internal_id) + " connection closed, killing actor");
            die();
        }
    }
}
