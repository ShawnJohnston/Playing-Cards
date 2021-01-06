import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    JButton playButton;
    JButton quitButton;

    GameFrame() {
        ImageIcon windowIcon = new ImageIcon("src/chip.png");

        playButton = new JButton();
        playButton.setText("Play");
        playButton.setVisible(true);
        playButton.setBounds(300,300,200,100);
        playButton.setHorizontalAlignment(JButton.CENTER);
        playButton.setVerticalAlignment(JButton.CENTER);

        quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setVisible(true);
        quitButton.setBounds(300,450,200,100);
        quitButton.setHorizontalAlignment(JButton.CENTER);
        quitButton.setVerticalAlignment(JButton.CENTER);


        this.setBackground(Color.BLACK);
        this.setTitle("Shawn Johnston - Poker Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(800,800);
        this.setVisible(true);
        this.setIconImage(windowIcon.getImage());
        this.add(playButton);
        this.add(quitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==playButton) {
        }
        if (e.getSource()==quitButton) {
        }
    }
}
