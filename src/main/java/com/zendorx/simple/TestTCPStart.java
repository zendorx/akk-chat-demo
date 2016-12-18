package com.zendorx.simple;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.net.InetSocketAddress;
import java.sql.ClientInfoStatus;

/**
 * Created by user on 24.10.2016.
 */
public class TestTCPStart {

    public static void main(String[] args)
    {
        ActorSystem serverActorSystem = ActorSystem.create("ServerActorSystem");

        ActorRef serverActor = serverActorSystem.actorOf(TestTCPServer.props(null), "serverActor");

        ActorSystem clientActorSystem = ActorSystem.create("ClientActorSystem");

        ActorRef clientActor = clientActorSystem.actorOf(
                TestTCPClient.props( new InetSocketAddress("localhost", 9090), null, "Hello world"), "clientActor");

        ActorRef clientActor2 = clientActorSystem.actorOf(
                TestTCPClient.props( new InetSocketAddress("localhost", 9090), null, "Hello world from second client!"), "clientActor2");

        serverActorSystem.awaitTermination();
        clientActorSystem.awaitTermination();
    }
}
