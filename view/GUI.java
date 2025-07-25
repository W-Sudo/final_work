package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import model.Card;
import controller.GameManager;

public class GUI {
    private JFrame frame;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private JLabel resultLabel;
    private JButton hitButton, standButton, retryButton;
    private GameManager gameManager;
    boolean haveFinish=false;
    String p_s="Player hands :";
    String d_s="Dealer hands :";

    public GUI(GameManager manager) {
        this.gameManager = manager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Blackjack");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // タイトル表示（ゲーム状況）
        resultLabel = new JLabel("ゲーム開始", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        frame.add(resultLabel, BorderLayout.NORTH);

        // 手札表示パネル（上下）
        JPanel handsPanel = new JPanel(new GridLayout(2, 1));
        playerHandLabel = new JLabel(p_s);
        dealerHandLabel = new JLabel(d_s);
        updatePlayerHands(gameManager.getPlayerHand());
        updateDealerHands(gameManager.getDealerHand(),false);
        handsPanel.add(playerHandLabel);
        handsPanel.add(dealerHandLabel);
        frame.add(handsPanel, BorderLayout.CENTER);

        // ボタンパネル
        JPanel buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        retryButton = new JButton("Retry");
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(retryButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // ボタン動作設定
        setupActionListeners();
        //updateHands();

        frame.setVisible(true);
    }

    private void setupActionListeners() {
        hitButton.addActionListener(e -> {
            if(!haveFinish){
                gameManager.playerHit();
                updatePlayerHands(gameManager.getPlayerHand());
            }
            if (gameManager.isPlayerBusted()) {
                haveFinish=true;
                showResult();
            }
        });

        standButton.addActionListener(e -> {
            gameManager.playerStand();
            if(!haveFinish){
                updateDealerHands(gameManager.getDealerHand(),true);
            }
            haveFinish=true;
            showResult();
        });

        retryButton.addActionListener(e -> {
            p_s="Player hands :";
            d_s="Dealer hands :";
            gameManager.restart();
            updatePlayerHands(gameManager.getPlayerHand());
            updateDealerHands(gameManager.getDealerHand(),false);
            haveFinish=false;
            resultLabel.setText("新しいゲームを開始");
        });
    }

    // 手札表示更新
    public void updatePlayerHands(ArrayList<Card> c) {
        String s = p_s;
        for(int i=0;i<c.size();i++){
            s=s+" "+c.get(i).toString();
        }
        playerHandLabel.setText(s);
    }
    public void updateDealerHands(ArrayList<Card> c,boolean haveStand) {
        String s = d_s;
        if(haveStand){
            for(int i=0;i<c.size();i++){
                s=s+" "+c.get(i).toString();
            }
        }else{
            s=s+" "+c.get(0).toString()+" ??";
        }
        dealerHandLabel.setText(s);
    }
    public void showResult() {
        resultLabel.setText(gameManager.getResult());
    }
}