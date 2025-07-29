package model;

public class Card{
    private int value;//カードの数値
    private String mark;//カードのマーク
    private int n;//識別用の数字

    //識別番号から変数を初期化する処理
    public Card(int n0){
        n=n0;
        value=n/4+1;
        switch (n % 4) {
            case 0: this.mark = "spade"; break;
            case 1: this.mark = "club"; break;
            case 2: this.mark = "diamond"; break;
            case 3: this.mark = "heart"; break;
        }
    }

    //値のゲッターメソッド
    public int getValue(){
        return value;
    }

    //マークのゲッターメソッド
    public String getMark(){
        return mark;
    }

    //GUIのために識別番号を得るゲッターメソッド
    public int getN(){
        return n;
    }

    //GUIのためマークと値をまとめて文字列にして返すメソッド
    public String toString(){
        String markGUI;
        if(this.mark.equals("spade")){
            markGUI="♠";
        }else if(this.mark.equals("club")){
            markGUI="♣";
        }else if(this.mark.equals("heart")){
            markGUI="♥";
        }else{
            markGUI="♦";
        }
        String value="A";
        if(this.value<=10&&this.value!=1){
            value=String.valueOf(this.value);
        }else if(this.value==11){
            value="J";
        }else if(this.value==12){
            value="Q";
        }else if(this.value==13){
            value="K";
        }
        String graphic = markGUI+value;
        return graphic;
    }
}