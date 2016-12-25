package com.zendorx.chat.internal;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import com.zendorx.chat.internal.core.Constant;
import com.zendorx.chat.internal.core.ZLog;

import java.net.InetSocketAddress;

public class Kernel extends UntypedActor {

    ZLog zlog = new ZLog("Kernel");



    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef tcpActor;
    private ActorRef manager;
    private int port = 9090;
    private String address = "localhost";
    private int connection_id = 0;

    public  static Props props(ActorRef tcpActor, final String address, int port) {
        return Props.create(Kernel.class, tcpActor, address, port);
    }

    @Override
    public void preStart()
    {
        zlog.m("Kernel preStart");

        manager = getContext().actorOf(ConnectionManager.props(), Constant.MANAGER_ID);

        if (tcpActor == null)
        {
            tcpActor = Tcp.get(getContext().system()).manager();
        }

        tcpActor.tell(TcpMessage.bind(getSelf(), new InetSocketAddress(address, port), 100), getSelf());
    }

    public Kernel(ActorRef tcpActor, final String address, int port)
    {
        zlog.m("Kernel Kernel");
        this.tcpActor = tcpActor;
        this.port = port;
        this.address = address;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Tcp.Bound)
        {
            zlog.m("Kernel has bound");
        }
        else
        if (message instanceof Tcp.CommandFailed)
        {
            zlog.w("Kernel has stopped");
            getContext().stop(getSelf());
        } else
        if (message instanceof Tcp.Connected)
        {
            zlog.m("Kernel has connected");
            connection_id++;

            ActorRef handler = getContext().actorOf(
                    ConnectionHandler.props(connection_id),
                    Constant.getConnectionHandlerID(connection_id));

            getSender().tell(TcpMessage.register(handler), getSelf());
        }
        else
        {
            zlog.e("Error: kernel unhandled message");
            unhandled(message);
        }
    }
}
