package com.zendorx.chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zendorx.chat.internal.Kernel;

public class Server {

    public static void start(String url, int port)
    {
        System.out.println("chat server is starting: " + url + " " + String.valueOf(port));

        Config config = ConfigFactory.parseString("akka { loglevel = \"OFF\" }");

        ActorSystem serverActorSystem = ActorSystem.create("chat_server_system", config);
        ActorRef kernel = serverActorSystem.actorOf(Kernel.props(null, url, port), "kernel");
        serverActorSystem.awaitTermination();

        System.out.println("chat server has stopped");
    }

    /*public static void main(String[] args)
    {
        String data = "{\"id\":\"" + ChatCommand.ID_REGISTER +"\",\"name\":\"Denis\"}";

        ActorSystem clientActorSystem = ActorSystem.create("chat_client1_system");
        ActorRef clientActor = clientActorSystem.actorOf(
                TestTCPClient.props( new InetSocketAddress("localhost", 9090), null, data), "clientActor");

    }*/
}
