package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.GameManager;

public class GUI {
    // 属性（フィールド）
    private JFrame frame;
    private JButton hitButton;
    private JButton standButton;
    private JButton retryButton;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private JLabel resultLabel;
    private GameManager gameManager;
    String p_s="Player hands :";
    String d_s="Dealer hands :";

    // コンストラクタ
    public GUI(GameManager manager) {
        this.gameManager = manager;
        initialize();  // GUI部品初期化
    }

    // GUI部品初期
    private void initialize() {
        frame = new JFrame("Blackjack");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 上部：勝敗表示
        resultLabel = new JLabel("ゲーム開始", SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.NORTH);

        // 中央：手札表示
        JPanel handPanel = new JPanel(new GridLayout(2, 1));
        playerHandLabel = new JLabel("Player: ");
        dealerHandLabel = new JLabel("Dealer: ");
        handPanel.add(playerHandLabel);
        handPanel.add(dealerHandLabel);
        frame.add(handPanel, BorderLayout.CENTER);

        // 下部：ボタン
        JPanel buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        retryButton = new JButton("Retry");

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(retryButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // アクションリスナー設定
        setupActionListeners();

        frame.setVisible(true);
    }

    // GameManager連携（アクションリスナー）
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

    // 勝敗結果表示
    public void showResult() {
        resultLabel.setText(gameManager.getResult());
    }
}