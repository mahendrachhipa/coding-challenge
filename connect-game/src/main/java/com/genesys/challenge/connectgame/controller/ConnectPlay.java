package com.genesys.challenge.connectgame.controller;

import com.genesys.challenge.connectgame.model.GameBoard;
import com.genesys.challenge.connectgame.model.PlayData;
import com.genesys.challenge.connectgame.service.ConnectGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ConnectPlay {
    @Autowired
    ConnectGameService connectGameService;

    @RequestMapping("/game/{playerName}")
    public ResponseEntity<GameBoard> startGame(@PathVariable String playerName) {
        return new ResponseEntity<>(connectGameService.createGame(playerName), HttpStatus.OK);
    }

    @RequestMapping("/gameinfo/{gameId}")
    public ResponseEntity<GameBoard> getGame(@PathVariable String gameId) {
        return new ResponseEntity<>(connectGameService.getGame(gameId), HttpStatus.OK);
    }

    @RequestMapping(value="/game",method = RequestMethod.POST)
    public ResponseEntity<GameBoard> playGame(@RequestBody PlayData playData) {
        return new ResponseEntity<>(connectGameService.play(playData),HttpStatus.OK);
    }
}
