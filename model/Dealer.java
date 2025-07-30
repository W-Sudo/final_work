package model;
import java.util.*;
//Playerクラスから継承
public class Dealer extends Player{
    public Dealer(Deck deck){
        super(deck);
    }
    @SuppressWarnings("unused")
    private ArrayList<Card> hands = new ArrayList<>();//共有しない手札の宣言
    //手札が17以上であるかを返す処理
    public boolean over_16(){
        return score>16;
    }
    //リセット処理
    public Dealer reset(Deck new_deck){
        Dealer reset = new Dealer(new_deck);
        return reset;
    }
}
