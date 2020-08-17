package com.genesys.challenge.connectgameclient;

import com.genesys.challenge.connectgameclient.player.ConnectGamePlayer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ConnectGameClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectGameClientApplication.class, args);
		ConnectGamePlayer connectGamePlayer = new ConnectGamePlayer();
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your name to start :");
		String playerName = sc.next();
		connectGamePlayer.startGame(playerName);
	}

}
