package model;

public class Player {
    protected Deck deck;
    public Player(Deck deck) {//GameManagerからDeckクラスの参照値を得る
        this.deck = deck;
    }
    private ArrayList<Card> hands = new ArrayList<>();//手札
    protected int score=0; //手札の合計
    protected boolean haveA=false; //Aを入手したかを見る変数
    protected boolean convA=false; //Aが1として計算されたかを見る変数
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
    public boolean isBurst(){  //バーストしたかを返す処理
        return score>21;
    }
    public Card hit(){//カードを引く処理
        hands.add(deck.drawCard());
        calcScore(hands.get(hands.size()-1));
        return hands.get(hands.size()-1);
    }
    public int getScore(){//スコアを返す処理
        return score;
    }
    public void reset(Deck new_deck){//リセット処理
        Player reset = new Player(new_deck);
        return reset;
    }
}
