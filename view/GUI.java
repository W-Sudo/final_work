package view;

import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
import java.awt.event.*;
=======
import java.util.List;
import model.Card;
import model.Card.Suit;
>>>>>>> 71aaf6ef1da45f5a5d5c41cded026c12bbd035c4
import controller.GameManager;

public class GUI {
    private JFrame frame;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel;
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
        playerHandPanel = new JPanel();
        dealerHandPanel = new JPanel();
        handsPanel.add(playerHandPanel);
        handsPanel.add(dealerHandPanel);
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
        updateHands();

        frame.setVisible(true);
    }

    private void setupActionListeners() {
        hitButton.addActionListener(e -> {
            gameManager.playerHit();
            p_s=updatePlayerHands(p_s);
            if (gameManager.isPlayerBusted()) {
                showResult();
            }
        });

        standButton.addActionListener(e -> {
            gameManager.playerStand();
            d_s=updateDealerHands(d_s);
            showResult();
        });

        retryButton.addActionListener(e -> {
            p_s="Player hands :";
            d_s="Dealer hands :";
            gameManager.restart();
            resultLabel.setText("新しいゲームを開始");
        });
    }

<<<<<<< HEAD
    // 手札表示更新
    public String updatePlayerHands(String s) {
        s=s+" "+gameManager.getPlayerHandString();
        playerHandLabel.setText(s);
        return s;
    }
    public String updateDealerHands(String s) {
        s=s+" "+gameManager.getDealerHandString();
        dealerHandLabel.setText(s);
        return s;
    }
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
        int suitOffset = switch (card.getSuit()) {
            case SPADE -> 0;
            case CLUB -> 13;
            case DIAMOND -> 26;
            case HEART -> 39;
        };
        return suitOffset + card.getNumber(); // number は 1〜13
>>>>>>> 71aaf6ef1da45f5a5d5c41cded026c12bbd035c4
    }

    public void showResult() {
        resultLabel.setText(gameManager.getResult());
    }
}