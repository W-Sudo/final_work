package model;
import java.util.*;

public class Dealer extends Player{
    public Dealer(Deck deck){
        super(deck);
    }
    private ArrayList<Card> hands = new ArrayList<>();//共有しない手札の宣言
    public boolean over_16(){//手札が17以上であるかを返す処理
        return score>16;
    }
    public Dealer reset(Deck new_deck){//リセット処理
        Dealer reset = new Dealer(new_deck);
        return reset;
    }
}
