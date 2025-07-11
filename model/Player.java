package model;

public class Player {
    private Deck deck;
    public Player(Deck deck) {
        this.deck = deck;
    }
    ArrayList<Card> hands = new ArrayList<>();
    private int score=0;
    private boolean haveA=false;
    private boolean convA=false;
    void calcScore(Card c){
        if(!haveA&&c.getValue()==1){
            haveA=true;
            score=score+11;
        }else{
            score=score+c.getValue();
        }
        if(score>21&&haveA&&!convA){
            score=score-10;
            convA=true;
        }
    }
    boolean isBurst(){
        return score>21;
    }
    Card hit(){
        hands.add(deck.drawCard());
        calcScore(hands.get(hands.size()-1));
        return hands.get(hands.size()-1);
    }
    int getScore(){
        return score;
    }

}
