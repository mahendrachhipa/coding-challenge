package com.genesys.challenge.connectgameclient;

import com.genesys.challenge.connectgameclient.player.ConnectGamePlayer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class ConnectGameClientApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ConnectGameClientApplication.class, args);
		ConnectGamePlayer connectGamePlayer = applicationContext.getBean(ConnectGamePlayer.class);
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your name to start :");
		String playerName = sc.next();
		connectGamePlayer.startGame(playerName);
	}
}
