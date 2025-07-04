package model;
import java.util.*;

public class Deck{
    private int n = 52;
    private final Card[] deck = new Card[n];
    public Deck(){
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        Random rand = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        for(int i=0;i<n;i++){
            deck[i]=new Card(arr[i]);
        }
    }
    public Card drawCard(){
        n=n-1;
        return deck[n];
    }
    public Deck reset(){
        Deck new_deck = new Deck();
        return new_deck;
    }
}