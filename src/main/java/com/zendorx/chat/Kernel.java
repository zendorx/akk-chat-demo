package com.zendorx.chat;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;

import java.net.InetSocketAddress;

public class Kernel extends UntypedActor {

    private int last_internal_id = 0;


    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  //  private ActorRef worker;
    private ActorRef tcpActor;
    private ActorRef manager;
    private int port = 9090;
    private String address = "localhost";

    public  static Props props(ActorRef tcpActor, final String address, int port) {
        return Props.create(Kernel.class, tcpActor, address, port);
    }

    @Override
    public void preStart()
    {
        log.info("Kernel preStart");
//        worker = getContext().actorOf(Props.create(ServerWorker.class), "worker");

        manager = getContext().actorOf(Manager.props(), Constant.MANAGER_ID);

        if (tcpActor == null)
        {
            tcpActor = Tcp.get(getContext().system()).manager();
        }

        tcpActor.tell(TcpMessage.bind(getSelf(), new InetSocketAddress(address, port), 100), getSelf());
    }

    public Kernel(ActorRef tcpActor, final String address, int port)
    {
        log.info("Kernel Kernel");
        this.tcpActor = tcpActor;
        this.port = port;
        this.address = address;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Tcp.Bound)
        {
            log.info("Kernel bound");
        }
        else
        if (message instanceof Tcp.CommandFailed)
        {
            log.info("Kernel stopped");
            getContext().stop(getSelf());
        } else
        if (message instanceof Tcp.Connected)
        {
            log.info("Kernel connected");
            //final Tcp.Connected conn = (Tcp.Connected) message;
            last_internal_id++;
            ActorRef handler = getContext().actorOf(ConnectionHandler.props(last_internal_id),
                    Constant.getConnectionHandlerID(last_internal_id));

            getSender().tell(TcpMessage.register(handler), getSelf());
        }
        else
        {
            log.info("Kernel unhandled");
            unhandled(message);
        }
    }
}
