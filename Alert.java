package teamproject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Alert extends Dialog implements ActionListener, KeyListener {
    private JPanel alertPanel = new JPanel();
    private JButton exitButton;
    private JLabel messageLabel;

    public Alert(JFrame f, String[] message) {
        super(f, "Alert", true);

        alertPanel.setLayout(new GridLayout(message.length + 1, 1));

        for (int i = 0; i < message.length; i++) {
            messageLabel = new JLabel(message[i], SwingConstants.CENTER);
            messageLabel.setFont(CustomFont.getDefaultFont());
            alertPanel.add(messageLabel);
        }

        exitButton = new JButton("확인");
        exitButton.setFont(CustomFont.getDefaultFont());

        alertPanel.add(exitButton);
        alertPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.add(alertPanel);

        exitButton.addActionListener(this);
        exitButton.addKeyListener(this);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("확인")) this.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == '\n' || e.getKeyCode() == '\r') this.dispose();
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}