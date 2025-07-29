package controller;

import java.util.*;

import model.Card;
import model.Dealer;
import model.Deck;
import model.Player;
import model.Chip;
import view.GUI;

public class GameManager {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private GUI gui;
    private Chip chip;

    //コンストラクタで他クラスのインスタンスを生成
    public GameManager(){
        deck = new Deck();
        player = new Player(deck);
        dealer = new Dealer(deck);
        startGame();
        gui = new GUI(this);
        chip = new Chip();
    }

    //ゲーム開始の処理
    public void startGame(){
        int bet = 10;
        chip.changeChip(bet);
        
        player.hit();
        player.hit();
        dealer.hit();
        dealer.hit();

    }

    //プレイヤーがhitしたときの処理
    public void playerHit(){
        player.hit();
    }

    //プレイヤーがstandしたときの処理
    public void playerStand(){
        dealerTurn();
    }

    //ディーラーのターンになったときの処理
    public void dealerTurn(){
        while(dealer.getScore() < 17){
            dealer.hit();
        }
    }

    //勝敗を決定する処理
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

    //ゲームを初期化させる処理
    public void restart(){
        deck=deck.reset();
        player=player.reset(deck);
        dealer=dealer.reset(deck);
        startGame();
    }

    //プレイヤーの手札のゲッターメソッド
    public ArrayList<Card> getPlayerHand(){
        return player.getCard();
    }

    //ディーラーの手札のゲッターメソッド
    public ArrayList<Card> getDealerHand(){
        return dealer.getCard();
    }

    //プレイヤーの手札がバーストしたかを判断するメソッド
    public boolean isPlayerBusted(){
        return player.isBurst();
    }

    //勝敗結果のゲッターメソッド
    public String getResult(){
        return judgeWinner();
    }

}
