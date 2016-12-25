package com.zendorx.chat.internal;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import com.zendorx.chat.internal.core.Api;
import com.zendorx.chat.internal.core.Command;

import java.util.List;

public class ConnectionHandler extends UntypedActor {

    private final int id;
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props(int id) {
        return Props.create(ConnectionHandler.class, id);
    }

    public ConnectionHandler(int id)
    {
        this.id = id;
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
        log.info("ConnectionHandler stopped, id: " + String.valueOf(id));

        /*if (manager != null)
        {
            manager.tell(new ConnectionManager.Unregister(id), getSelf());
        }*/
    }

    @Override
    public void preStart()
    {
        log.info("ConnectionHandler created, id: " + String.valueOf(id));

    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof Tcp.Received)
        {
            log.info("ConnectionHandler Received");
            final String data = ((Tcp.Received) message).data().utf8String();
            List<Object> objects = Api.makeCommands(data);

            /*if (object != null)
            {
               // worker.tell(object, getSelf());
                manager = getContext().actorFor("../" + Constant.MANAGER_ID);
                manager.tell(new ConnectionManager.Register(id), getSelf());
            }
            else
            {
                log.info("ConnectionHandler command parse error");
                die();
            }*/

            /*log.info("In SimplisticHandlerActor - Received message: " + data);
            getSender().tell(TcpMessage.write(ByteString.fromArray(("echo " + data).getBytes())), getSelf());*/

        } else
        if (message instanceof Tcp.ConnectionClosed) {

            log.info("ConnectionHandler " + String.valueOf(id) + " connection closed, killing actor");
            die();
        }
    }
}
