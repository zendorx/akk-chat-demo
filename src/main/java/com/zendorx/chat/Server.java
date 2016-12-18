package com.zendorx.chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.zendorx.simple.TestTCPClient;

import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args)
    {
        System.out.println("chat server is starting..");
        ActorSystem serverActorSystem = ActorSystem.create("chat_server_system");
        ActorRef kernel = serverActorSystem.actorOf(Kernel.props(null, "localhost", 9090), "kernel");
        String data = "{\"id\":\"" + ChatCommand.ID_REGISTER +"\",\"name\":\"Denis\"}";

        ActorSystem clientActorSystem = ActorSystem.create("chat_client1_system");
        ActorRef clientActor = clientActorSystem.actorOf(
                TestTCPClient.props( new InetSocketAddress("localhost", 9090), null, data), "clientActor");

        serverActorSystem.awaitTermination();
        System.out.println("chat server has stopped");
    }
}
