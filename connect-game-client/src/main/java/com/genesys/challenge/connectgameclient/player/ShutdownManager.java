package com.genesys.challenge.connectgameclient.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/*
 *This class is responsible for exiting the application once game is complete.
 */
@Component
public class ShutdownManager {
    @Autowired
    ApplicationContext applicationContext;

    public void initiateShutdown(int exitcode) {
        SpringApplication.exit(applicationContext,()->exitcode);
    }

}
