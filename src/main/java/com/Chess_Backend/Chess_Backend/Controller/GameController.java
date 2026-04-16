package com.Chess_Backend.Chess_Backend.Controller;

import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SnakeAndLadder")
public class GameController {

    private Map<Integer, Integer> snake = Map.of(
            34, 3,
            45, 31,
            65, 4,
            79, 51,
            97, 78
    );

    private Map<Integer, Integer> ladder = Map.of(
            14, 35,
            25, 59,
            56, 76,
            29, 89
    );

    private int playerPosition = 0;

    @GetMapping("/Start")
    public String startGame() {
        playerPosition = 0; 
        return "Game Started";
    }

    @GetMapping("/Roll-Dice")
    public Map<String, Object> rollDie() {

        Random random = new Random();
        int dice = random.nextInt(6) + 1;

        String event = "normal";
        int newPosition = playerPosition + dice;

        if (newPosition > 100) {
            event = "retry";
        } else {
            playerPosition = newPosition;

            if (ladder.containsKey(playerPosition)) {
                playerPosition = ladder.get(playerPosition);
                event = "ladder";
            } else if (snake.containsKey(playerPosition)) {
                playerPosition = snake.get(playerPosition);
                event = "snake";
            }

            if (playerPosition == 100) {
                event = "win";
            }
        }

        return Map.of(
            "player", 1,
            "dice", dice,
            "position", playerPosition,
            "event", event
        );
    }
}