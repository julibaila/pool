package pool;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Ventana extends JPanel {
    private Tablero ballPool;
    private Palo palo;
    private Image backgroundImage;

    public Ventana() {
        palo = new Palo(350, 500, 100, 10, Color.BLACK);
        ballPool = new Tablero(palo);

        try {
            backgroundImage = ImageIO.read(new URL("https://www.seekpng.com/png/full/88-888900_pool-table-top-view-png-billiard-table.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                palo.setY(mouseY - palo.getHeight() / 2); 
                palo.setX(mouseX - palo.getWidth() / 2);

                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                 if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    ballPool.hitBall(palo);
                }
            }
        });
        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        List<Bolas> balls = ballPool.getBalls();

        palo.draw(g);

        for (Bolas ball : balls) {
            int x = ball.getX();
            int y = ball.getY();
            int radius = ball.getRadius();
            Color color = ball.getColor();
            g.setColor(color);
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }

    public void updateBalls() {
        ballPool.update();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bolas en el Pool");
            Ventana panel = new Ventana();
            frame.add(panel);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            Timer timer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.updateBalls();
                }
            });

            timer.start();
        });
    }
}
