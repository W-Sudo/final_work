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
    private boolean haveFinish=false;
    private final String p_s="Player hands :";
    private final String d_s="Dealer hands :";
    private JPanel startPanel;

    public GUI(GameManager manager) {
        this.gameManager = manager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Blackjack");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());

        createStartPanel();
        createGamePanel();

        // タイトル表示（ゲーム状況）
        resultLabel = new JLabel("ゲーム開始", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        frame.add(resultLabel, BorderLayout.NORTH);

        // 手札表示パネル（上下）
        JPanel handsPanel = new JPanel(new GridLayout(2, 1));
        playerHandLabel = new JLabel(p_s);
        playerHandLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
        dealerHandLabel = new JLabel(d_s);
        dealerHandLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
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
        
        frame.setVisible(true);
    }

    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("ブラックジャックへようこそ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        JButton startButton = new JButton("ゲームスタート");
        startButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(gamePanel); // 後述のgamePanel（ゲーム画面）へ
            frame.revalidate();
            frame.repaint();
            gameManager.startNewGame(); // 必要に応じて
        });
        startPanel.add(titleLabel, BorderLayout.CENTER);
        startPanel.add(startButton, BorderLayout.SOUTH);
        frame.add(startPanel, BorderLayout.CENTER);
    }

    //ボタン処理
    private void setupActionListeners() {
        //hitボタン処理
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

        //standボタン処理
        standButton.addActionListener(e -> {
            gameManager.playerStand();
            if(!haveFinish){
                updateDealerHands(gameManager.getDealerHand(),true);
            }
            haveFinish=true;
            showResult();
        });

        retryButton.addActionListener(e -> {
            gameManager.restart();
            updatePlayerHands(gameManager.getPlayerHand());
            updateDealerHands(gameManager.getDealerHand(),false);
            haveFinish=false;
            resultLabel.setText("新しいゲームを開始");
        });
    }

    // プレイヤーの手札表示更新
    public void updatePlayerHands(ArrayList<Card> c) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>"+p_s);
        
        // 操作によって色付きの行を追加
        for(int i=0;i<c.size();i++){
            if(c.get(i).getMark()=="heart"||c.get(i).getMark()=="diamond"){
                sb.append("<span style='color:red;'> "+c.get(i).toString()+"</span>");
            }else{
                sb.append(" "+c.get(i).toString());
            }
        }
        sb.append("</html>");
        playerHandLabel.setText(sb.toString());
    }

    // ディーラーの手札表示更新
    public void updateDealerHands(ArrayList<Card> c,boolean haveStand) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>"+d_s);
        if(haveStand){
            for(int i=0;i<c.size();i++){
                if(c.get(i).getMark()=="heart"||c.get(i).getMark()=="diamond"){
                    sb.append("<span style='color:red;'> "+c.get(i).toString()+"</span>");
                }else{
                    sb.append(" "+c.get(i).toString());
                }
            }
        }else{
            if(c.get(0).getMark()=="heart"||c.get(0).getMark()=="diamond"){
                sb.append("<span style='color:red;'> "+c.get(0).toString()+"</span>");
            }else{
                sb.append(" "+c.get(0).toString());
            }
            sb.append(" ??");
        }
        dealerHandLabel.setText(sb.toString());
    }

    //結果の表示
    public void showResult() {
        resultLabel.setText(gameManager.getResult());
    }
}