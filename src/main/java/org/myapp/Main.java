package org.myapp;
import fi.iki.elonen.NanoHTTPD;
import models.AdvancedModel;
import models.MediumModel;
import models.SimpleModel;
import router.RouterChainFactory;
import router.RouterService;
import web.WebServer;

import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException {

        String ollamaBaseUrl = "http://localhost:11434";
        SimpleModel simple = new SimpleModel(ollamaBaseUrl, "gemma3:1b-it-q4_K_M");
        MediumModel medium = new MediumModel(ollamaBaseUrl, "gemma3:1b");
        AdvancedModel advanced = new AdvancedModel(ollamaBaseUrl, "gemma3:1b-it-fp16");
        RouterService service = new RouterService(
                RouterChainFactory.createRouterChain(ollamaBaseUrl, "gemma3:1b", simple, medium, advanced),
                cache.CaffeineCacheManager.getInstance(1000, 3600)
        );

        WebServer server = new WebServer(9090, service);
        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server running at http://localhost:9090");
    }

}
