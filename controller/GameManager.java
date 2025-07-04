package final_work.controller;

import final_work.view.GUI;

public class GameManager {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private GUI gui;

    GameManager(){
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        gui = new GUI();
    }

    void startGame(){
    }

    void playerHit(){

    }

    void playerStand(){

    }

    void dealerTurn(){

    }

    String judgeWinner(){

    }

    void resetGame(){

    }
}
