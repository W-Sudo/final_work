package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
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
            gameManager.playerHit();
            updatePlayerHands(gameManager.getPlayerHand());
            if (gameManager.isPlayerBusted()) {
                showResult();
            }
        });

        standButton.addActionListener(e -> {
            gameManager.playerStand();
            updateDealerHands(gameManager.getDealerHand(),true);
            showResult();
        });

        retryButton.addActionListener(e -> {
            p_s="Player hands :";
            d_s="Dealer hands :";
            gameManager.restart();
            updatePlayerHands(gameManager.getPlayerHand());
            updateDealerHands(gameManager.getDealerHand(),false);
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
    /*public void updateHands() {
        playerHandLabel.removeAll();
        dealerHandLabel.removeAll();

        displayCards(playerHandLabel, gameManager.getPlayerHand());
        displayCards(dealerHandLabel, gameManager.getDealerHand());

        playerHandLabel.revalidate();
        dealerHandLabel.revalidate();
        playerHandLabel.repaint();
        dealerHandLabel.repaint();
    }*/

    private void displayCards(JPanel panel, List<Card> hand) {
        for (Card card : hand) {
            int imageNumber = getImageNumberFromCard(card);
            String filename = "images/torannpu-illust" + imageNumber + ".png";
            ImageIcon icon = new ImageIcon(filename);

            // 画像が読み込めない場合のデバッグ出力
            if (icon.getIconWidth() == -1) {
                System.err.println("画像が見つかりません: " + filename);
                continue;
            }

            Image scaled = icon.getImage().getScaledInstance(80, 110, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaled));
            panel.add(label);
        }
    }

    // Card から画像番号（1〜52）を計算
    private int getImageNumberFromCard(Card card) {
        int suitOffset = card.getN();
        return suitOffset;
    }

    public void showResult() {
        resultLabel.setText(gameManager.getResult());
    }
}