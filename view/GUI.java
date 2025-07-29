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
    private JButton hitButton, standButton;
    private GameManager gameManager;
    private boolean haveFinish=false;
    private final String p_s="Player hands :";
    private final String d_s="Dealer hands :";
    private JPanel startPanel;
    private JPanel endPanel;
    private JPanel gamePanel; 
    private JPanel handsPanel = new JPanel(new GridLayout(2, 1));;// ← フィールドとして追加

    public GUI(GameManager manager) {
        this.gameManager = manager;
        this.gameManager.setGUI(this);
        initialize();
    }

    private void initialize() {
       frame = new JFrame("Blackjack");
       frame.setSize(800, 500);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLayout(new BorderLayout());

       createGamePanel();
       createStartPanel();
       
       frame.add(startPanel, BorderLayout.CENTER);
       frame.setVisible(true);
    }

    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Blackjack!!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JButton startButton = new JButton("Game Start");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 24));

        startButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
        });

        startPanel.add(titleLabel, BorderLayout.CENTER);
        startPanel.add(startButton, BorderLayout.SOUTH);
    }

    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        // タイトル表示（ゲーム状況）
        resultLabel = new JLabel("ゲーム開始", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gamePanel.add(resultLabel, BorderLayout.NORTH);
        // 手札表示
        playerHandLabel = new JLabel(p_s);
        playerHandLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
        dealerHandLabel = new JLabel(d_s);
        dealerHandLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
        updatePlayerHands(gameManager.getPlayerHand());
        updateDealerHands(gameManager.getDealerHand(), false);
        handsPanel.add(playerHandLabel);
        handsPanel.add(dealerHandLabel);
        gamePanel.add(handsPanel, BorderLayout.CENTER);

       // ボタン類
       JPanel buttonPanel = new JPanel();
       hitButton = new JButton("Hit");
       standButton = new JButton("Stand");
       
       buttonPanel.add(hitButton);
       buttonPanel.add(standButton);
       
       gamePanel.add(buttonPanel, BorderLayout.SOUTH);

       setupActionListeners(); // ボタンのイベントもセット
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
                 gameManager.finishGame(gameManager.getResult());
            }
        });

        //standボタン処理
        standButton.addActionListener(e -> {
            gameManager.playerStand();
            if(!haveFinish){
                updateDealerHands(gameManager.getDealerHand(),true);
            }
            haveFinish=true;
            gameManager.finishGame(gameManager.getResult());
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

    public void showEndScreen(String resultText) {
        endPanel = new JPanel(new BorderLayout());
        resultLabel = new JLabel(resultText, SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel buttonPanel = new JPanel();
        JButton retryButton = new JButton("もう一度プレイ");
        JButton exitButton = new JButton("終了");

        retryButton.addActionListener(e -> {
            // ゲームロジックを初期化
            gameManager.restart();
            haveFinish = false;
            frame.getContentPane().removeAll();
            frame.add(gamePanel); // ゲーム画面に戻す
            frame.revalidate();
            frame.repaint();
            // ★ 再描画・再表示（追加）
            resultLabel.setText("新しいゲームを開始");
            updatePlayerHands(gameManager.getPlayerHand());
            updateDealerHands(gameManager.getDealerHand(), false);
            gamePanel.add(handsPanel, BorderLayout.CENTER);

        });

        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(retryButton);
        buttonPanel.add(exitButton);

        endPanel.add(resultLabel, BorderLayout.NORTH);
        endPanel.add(handsPanel, BorderLayout.CENTER);
        endPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(endPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    } 
}