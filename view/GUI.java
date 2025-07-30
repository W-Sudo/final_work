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
    private JButton hitButton, standButton,doubleDownButton;
    private GameManager gameManager;
    private boolean haveFinish=false;
    private final String p_s="Player hands :";
    private final String d_s="Dealer hands :";
    private JPanel startPanel;
    private JPanel betPanel;
    private JPanel endPanel;
    private JPanel gamePanel; 
    private JPanel handsPanel;
    private JPanel topPanel;// ← フィールドとして追加
    private JLabel chipAmountLabel;
    private JLabel topLabel;
    private int currentBet = 100;

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
       createStartPanel();
       createBetPanel();
       createGamePanel();
       betPanel.add(topPanel,BorderLayout.NORTH);
       frame.add(startPanel, BorderLayout.CENTER);
       frame.setVisible(true);
    }
    //スタート画面の設定
    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Blackjack!!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        JButton startButton = new JButton("Select Bet");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 24));

        startButton.addActionListener(e -> {
            toBetPanel();
        });

        startPanel.add(titleLabel, BorderLayout.CENTER);
        startPanel.add(startButton, BorderLayout.SOUTH);
    }
    //別途画面の設定
    private void createBetPanel() {
        betPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new GridLayout(2, 1));
        topLabel = new JLabel("Select your bet !", SwingConstants.CENTER);
        topLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        chipAmountLabel = new JLabel("You Have " + gameManager.getChip() + " Chips", SwingConstants.CENTER);
        chipAmountLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        topPanel.add(topLabel);
        topPanel.add(chipAmountLabel);
        betPanel.add(topPanel, BorderLayout.NORTH);
        // スライダーで金額調整
        JPanel sliderPanel = new JPanel(new GridLayout(2, 1));
        JSlider betSlider = new JSlider(10, 1000, currentBet);
        betSlider.setMajorTickSpacing(100);
        betSlider.setMinorTickSpacing(10);
        betSlider.setPaintTicks(true);
        betSlider.setPaintLabels(true);
        betSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(10, new JLabel("10"));
        for (int i = 100; i <= 1000; i += 100) {
            labelTable.put(i, new JLabel(String.valueOf(i)));
        }
        betSlider.setLabelTable(labelTable);

        JLabel betAmountLabel = new JLabel("Current Bet: " + currentBet + " Chips", SwingConstants.CENTER);
        betAmountLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sliderPanel.add(betAmountLabel);
        sliderPanel.add(betSlider);
        betPanel.add(sliderPanel, BorderLayout.CENTER);

        betSlider.addChangeListener(e -> {
            currentBet = betSlider.getValue();
            betAmountLabel.setText("Current Bet: " + currentBet/10*10 + " Chip");
        });

        JButton startGameButton = new JButton("Game Start");
        startGameButton.addActionListener(e -> {
            gameManager.setCurrentBet(currentBet);
            toGamePanel();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);
        betPanel.add(buttonPanel, BorderLayout.PAGE_END);
    }
    //ゲーム画面の設定
    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        // タイトル表示（ゲーム状況）
        handsPanel = new JPanel(new GridLayout(2, 1));
        topLabel.setText("Game Start!!");
        chipAmountLabel.setText("You Have " + gameManager.getChip() + " Chips");
        gamePanel.add(topPanel, BorderLayout.NORTH);
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
        doubleDownButton = new JButton("Double Down");
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(doubleDownButton);
        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        setupActionListeners();
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
        //doubleDownボタン処理
        doubleDownButton.addActionListener(e -> {
            if(!haveFinish){
                gameManager.doubleDown();
                gameManager.playerHit();
                updatePlayerHands(gameManager.getPlayerHand());
            }
            if (gameManager.isPlayerBusted()) {
                haveFinish=true;
                gameManager.finishGame(gameManager.getResult());
            }
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
    //ベット画面を表示する
    public void toBetPanel(){
        frame.getContentPane().removeAll();
        frame.add(betPanel); // ゲーム画面に戻す
        betPanel.add(topPanel,BorderLayout.NORTH);
        topLabel.setText("Select your bet !");
        chipAmountLabel.setText("You Have " + gameManager.getChip() + " Chips");
        frame.revalidate();
        frame.repaint();
    }
    //Game画面を表示する
    public void toGamePanel(){
        gameManager.restart();
        haveFinish = false;
        frame.getContentPane().removeAll();
        frame.add(gamePanel);
        gamePanel.add(topPanel,BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
        topLabel.setText("Game Start!");
        chipAmountLabel.setText("You Have " + gameManager.getChip() + " Chips");
        updatePlayerHands(gameManager.getPlayerHand());
        updateDealerHands(gameManager.getDealerHand(), false);
        gamePanel.add(handsPanel, BorderLayout.CENTER);
    }
    //結果の表示
    public void showEndScreen(String resultText) {
        endPanel = new JPanel(new BorderLayout());
        JPanel resultPanel = new JPanel(new GridLayout(2,1));
        resultLabel = new JLabel(resultText, SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        JLabel changedChip = new JLabel("You Have " + gameManager.getChip() + " Chips",SwingConstants.CENTER);
        changedChip.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultPanel.add(resultLabel);
        resultPanel.add(changedChip);
        JPanel buttonPanel = new JPanel();
        JButton retryButton = new JButton("Retry");
        JButton exitButton = new JButton("Finish");

        retryButton.addActionListener(e -> {
            toBetPanel();
        });

        exitButton.addActionListener(e -> System.exit(0));

        if(!gameManager.isGameOver()){
            buttonPanel.add(retryButton);
        }
        buttonPanel.add(exitButton);
        endPanel.add(resultPanel, BorderLayout.NORTH);
        endPanel.add(handsPanel, BorderLayout.CENTER);
        endPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().removeAll();
        frame.add(endPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    } 
}