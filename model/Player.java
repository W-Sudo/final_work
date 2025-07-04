public class Player {
    private Deck deck;
    public Player(Deck deck) {
        this.deck = deck;
    }
    ArrayList<Card> hands = new ArrayList<>();
    public void hit(){
        hands.add(deck.drawCard());
    }
}
