package model;

public class Dealer extends Player{
    Dealer(Deck deck){
        super(deck);
    }
    private ArrayList<Card> hands = new ArrayList<>();//共有しない手札の宣言
    boolean over_16(){//手札が17以上であるかを返す処理
        return score>16;
    }
    void reset(Deck new_deck){//リセット処理
        Dealer reset = new Dealer(new_deck);
        return reset;
    }
}
