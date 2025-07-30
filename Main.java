import javax.swing.UIManager;

import controller.GameManager;

public class Main {
    public static void main(String[] args){
        //使用しているGUIのアップデート
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GameManager();
    }
}