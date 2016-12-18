package com.zendorx.simple;

import java.net.InetSocketAddress;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.Tcp.Bound;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;
//import com.testchat.trash.ServerUserHandler;

public class TestTCPServer extends UntypedActor
{
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private int index = 0;
    private ActorRef tcpActor;

    public  static Props props(ActorRef tcpActor) {
        return Props.create(TestTCPServer.class, tcpActor);
    }

    public TestTCPServer(ActorRef tcpActor)
    {
        this.tcpActor = tcpActor;
    }

    @Override
    public  void preStart() throws  Exception
    {
        if (tcpActor == null)
        {
            tcpActor = Tcp.get(getContext().system()).manager();
        }

        tcpActor.tell(TcpMessage.bind(getSelf(), new InetSocketAddress("localhost", 9090), 100), getSelf());
    }

    @Override
    public  void onReceive(Object msg) throws  Exception {
        if (msg instanceof Bound)
        {
            log.info("In serverActor - received message: bound");
        }
        else if (msg instanceof Tcp.CommandFailed)
        {
            getContext().stop(getSelf());
        }
        else if (msg instanceof Connected)
        {
            final Connected conn = (Connected) msg;
            log.info("In serverActor - received message: connected");
            index ++;
//            final ActorRef handler = getContext().actorOf(
//                    Props.create(ServerUserHandler.class), "user_" + String.valueOf(index)
            //);

//            getSender().tell(TcpMessage.register(handler), getSelf());
        }

    }

}
