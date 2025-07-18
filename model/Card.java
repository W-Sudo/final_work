package model;

public class Card{
    private int value;//カードの数値
    private String mark;//カードのマーク
    private int n;//識別用の数字
    public Card(int n0){//識別番号から変数を初期化する処理
        n=n0;
        value=n/4+1;
        switch (n % 4) {
            case 0: this.mark = "spade"; break;
            case 1: this.mark = "club"; break;
            case 2: this.mark = "diamond"; break;
            case 3: this.mark = "heart"; break;
        }
    }
    public int getValue(){//値のゲッターメソッド
        return value;
    }
    public String getMark(){//マークのゲッターメソッド
        return mark;
    }
    public int getN(){//GUIのために識別番号を得るゲッターメソッド
        return n;
    }
}