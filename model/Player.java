package model;
import java.util.*;

public class Player {
    protected Deck deck;
    public Player(Deck deck) {//GameManagerからDeckクラスの参照値を得る
        this.deck = deck;
    }
    private ArrayList<Card> hands = new ArrayList<>();//手札
    protected int score=0; //手札の合計
    protected boolean haveA=false; //Aを入手したかを見る変数
    protected boolean convA=false; //Aが1として計算されたかを見る変数
    
    //スコア計算を行うメソッド
    public void calcScore(Card c){
        if(!haveA&&c.getValue()==1){ //Aを入手したときの手札計算
            haveA=true;
            score=score+11;
        }else if(c.getValue()>10){   //絵札を入手したときの手札計算
            score=score+10;
        }else{                       //その他のカードを入手したときの手札計算
            score=score+c.getValue();
        }
        if(score>21&&haveA&&!convA){ //Aを11から1にする処理
            score=score-10;
            convA=true;
        }
    }

    //バーストしたかを返す処理
    public boolean isBurst(){
        return score>21;
    }

    //カードを引く処理
    public void hit(){
        hands.add(deck.drawCard());
        calcScore(hands.get(hands.size()-1));
    }

    //手札情報に関するゲッターメソッド
    public ArrayList<Card> getCard(){
        return hands;
    }

    //スコアのゲッターメソッド
    public int getScore(){
        return score;
    }

    //リセット処理
    public Player reset(Deck new_deck){
        Player reset = new Player(new_deck);
        return reset;
    }
}
