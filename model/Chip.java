package model;

public class Chip {
    private int chip;
    //チップの設定
    public Chip(){
        chip = 1000;
    }
    //チップのゲッターメソッド
    public int getChip(){
        return chip;
    }
    //チップの変更を行う処理
    public void changeChip(int dc){
        chip = chip + dc;
    }
    //チップが残っているかを返す処理
    public boolean isGameOver(){
        return chip<1;
    }
}
