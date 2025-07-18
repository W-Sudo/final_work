package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Card;
import model.Card.Suit;
import controller.GameManager;

public class GUI {
    private JFrame frame;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel;
    private JLabel resultLabel;
    private JButton hitButton, standButton, retryButton;
    private GameManager gameManager;

    public GUI(GameManager manager) {
        this.gameManager = manager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Blackjack");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        resultLabel = new JLabel("ゲーム開始", SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.NORTH);

        JPanel handPanel = new JPanel(new GridLayout(2, 1));
        playerHandPanel = new JPanel();
        dealerHandPanel = new JPanel();
        handPanel.add(playerHandPanel);
        handPanel.add(dealerHandPanel);
        frame.add(handPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        retryButton = new JButton("Retry");
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(retryButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        setupActionListeners();
        updateHands();

        frame.setVisible(true);
    }

    private void setupActionListeners() {
        hitButton.addActionListener(e -> {
            gameManager.playerHit();
            updateHands();
            if (gameManager.isPlayerBusted()) {
                showResult();
            }
        });

        standButton.addActionListener(e -> {
            gameManager.playerStand();
            updateHands();
            showResult();
        });

        retryButton.addActionListener(e -> {
            gameManager.restart();
            updateHands();
            resultLabel.setText("新しいゲームを開始");
        });
    }

    public void updateHands() {
        playerHandPanel.removeAll();
        dealerHandPanel.removeAll();

        displayCards(playerHandPanel, gameManager.getPlayerHand());
        displayCards(dealerHandPanel, gameManager.getDealerHand());

        playerHandPanel.revalidate();
        dealerHandPanel.revalidate();
        playerHandPanel.repaint();
        dealerHandPanel.repaint();
    }

    private void displayCards(JPanel panel, List<Card> hand) {
        for (Card card : hand) {
            int imageNumber = calculateImageNumber(card);
            String filename = "images/torannpu-illust" + imageNumber + ".png";
            ImageIcon icon = new ImageIcon(filename);
            Image scaled = icon.getImage().getScaledInstance(80, 110, Image.SCALE_SMOOTH);
            panel.add(new JLabel(new ImageIcon(scaled)));
        }
    }

    private int calculateImageNumber(Card card) {
        int base = switch (card.getSuit()) {
            case SPADE -> 0;
            case CLUB -> 13;
            case DIAMOND -> 26;
            case HEART -> 39;
        };
        return base + card.getNumber(); // number は 1〜13
    }

    public void showResult() {
        resultLabel.setText(gameManager.getResult());
    }
}