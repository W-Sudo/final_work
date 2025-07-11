package controller;

import view.GUI;
import model.Dealer;
import model.Deck;
import model.Player;

public class GameManager {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private GUI gui;

    public GameManager(){
        startGame();
        gui = new GUI(this);
    }

    public void startGame(){
        deck = new Deck();
        player = new Player(deck);
        dealer = new Dealer(deck);
        
        //山札から2枚引く
        player.hit();
        player.hit();
        dealer.hit();
        dealer.hit();

    }

    public void playerHit(){
        player.hit();
    }

    public void playerStand(){
        dealerTurn();
    }

    public void dealerTurn(){
        while(dealer.getScore() < 17){
            dealer.hit();
        }
    }

    public String judgeWinner(){
        int p = player.getScore();
        int d = dealer.getScore();

        if(p > 21){
            return "Player Burst! Dealer Wins.";
        }else if(d > 21){
            return "Dealer Burst! Player Wins.";
        }else if(p > d){
            return "Player Wins!";
        }else if(p < d){
            return "Dealer Wins!";
        }else{
            return "Draw!";
        }
    }

    public void resetGame(){
        startGame();
        gui.updateHands();
    }

    public String getPlayerHandString(){
        return player.toString();
    }

    publo String getDealerHandString(){
        return dealer.toString();
    }

    public boolean isPlayerBusted(){
        return player.isBurst();
    }

    public String getResult(){
        return judgeWinner();
    }

    public void restart(){
        resetGame();
    }
}
