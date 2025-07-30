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
    private int bet;
    //コンストラクタで他クラスのインスタンスを生成
    public GameManager(){
        deck = new Deck();
        chip = new Chip();
        player = new Player(deck);
        dealer = new Dealer(deck);
        startGame();
        gui = new GUI(this);
    }
    //ゲーム開始の処理
    public void startGame(){
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
        if(p == 21 && player.getCard().size()==2){
            chip.changeChip(bet/10*25);
            return "Black Jack!!";
        }else if(p > 21){
            if(chip.isGameOver()){
                return "Game Over";
            }
            return "Player Burst! Dealer Wins.";
        }else if(d > 21){
            chip.changeChip(2*bet);
            return "Dealer Burst! Player Wins.";
        }else if(p > d){
            chip.changeChip(2*bet);
            return "Player Wins!";
        }else if(p < d){
            if(chip.isGameOver()){
                return "Game Over";
            }
            return "Dealer Wins!";
        }else{
            chip.changeChip(bet);
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
    //チップ数のゲッターメソッド
    public int getChip(){
        return chip.getChip();
    }
    //チップが残っているかを調べるメソッド
    public boolean isGameOver(){
        return chip.isGameOver();
    }
    //ベットの情報を入手するメソッド
    public void setCurrentBet(int b){
        bet = b;
        chip.changeChip(-b);
    };
    //ダブルダウンしたときの処理
    public void doubleDown(){
        chip.changeChip(-bet);
        bet=bet*2;
    }
    //GUIにGameManagerの参照値を与えるメソッド
    public void setGUI(GUI gui) {
       this.gui = gui;
    }
    //ゲーム終了時の処理
    public void finishGame(String resultText) {
        if (gui != null) {
            gui.showEndScreen(resultText);
        }
    }
}
