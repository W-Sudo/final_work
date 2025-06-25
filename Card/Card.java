public class Card{
    private int value;//カードの数値
    private String mark;//カードのマーク
    private int n;//識別用の数字
    public Card(int n0){
        n=n0;
        value=n/4+1;
        switch (num % 4) {
            case 0: this.mark = "spade"; break;
            case 1: this.mark = "club"; break;
            case 2: this.mark = "heart"; break;
            case 3: this.mark = "diamond"; break;
        }
    }
    public int getValue(){
        return value;
    }
    public String getMark(){
        return mark;
    }
}