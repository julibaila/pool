package pool;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private List<Bolas> Bolas;
    private Palo palo;


    public Tablero(Palo palo) {
        this.palo = palo;
        Bolas = new ArrayList<>();
        Color colorBola1 = Color.white;
        Color colorBola2 = Color.black;
        Bolas ball1 = new Bolas(200, 300, 10, 10, 10,colorBola1);
        Bolas ball2 = new Bolas(250, 300, 10, 10, 10,colorBola2);
        Bolas.add(ball1);
        Bolas.add(ball2);
    }


    public List<Bolas> getBalls() {	
        return Bolas;
    }

    public void update() {
        for (Bolas ball : Bolas) {
            if (ball.getIsMoving()) {
                double friccion = 1.00;
                ball.setDx((int) (ball.getDx() * friccion));
                ball.setDy((int) (ball.getDy() * friccion));

                ball.move();

                if (ball.getX() - ball.getRadius() < 0 || ball.getX() + ball.getRadius() > 800) {
                    ball.setDx(-ball.getDx());
                }
                if (ball.getY() - ball.getRadius() < 0 || ball.getY() + ball.getRadius() > 600) {
                    ball.setDy(-ball.getDy());
                }

                // Check for collisions with other balls.
                for (Bolas otherBall : Bolas) {
                    if (ball != otherBall && collideWithAnotherBall(ball, otherBall)) {
                        ball.collideWithAnotherBall(otherBall);
                    }
                }
            }
        }

        for (Bolas ball : Bolas) {
            if (isCollision(ball, palo)) {
                ball.setDy(-ball.getDy());
            }
        }
    }

    public void hitBall(Palo palo) {
        for (Bolas ball : Bolas) {
            if (isCollision(ball, palo)) {
                int dx = ball.getX() - (palo.getX() + palo.getWidth() / 2);
                int dy = ball.getY() - (palo.getY() + palo.getHeight() / 2);
                double angle = Math.atan2(dy, dx);

                double speed = Math.sqrt(dx * dx + dy * dy);
                ball.setDx((int) (speed * Math.cos(angle)));
                ball.setDy((int) (speed * Math.sin(angle)));
                ball.setIsMoving(true);
            }
        }
    }


    private boolean isCollision(Bolas ball, Palo palo) {
        Rectangle ballBounds = new Rectangle(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), 2 * ball.getRadius(), 2 * ball.getRadius());
        Rectangle paloBounds = palo.getBounds();
        return ballBounds.intersects(paloBounds);
    }
    public boolean collideWithAnotherBall(Bolas ball1, Bolas ball2) {
        double distance = Math.sqrt((ball1.x - ball2.x) * (ball1.x - ball2.x) + (ball1.y - ball2.y) * (ball1.y - ball2.y));

        return distance <= (ball1.radio + ball2.radio);
    }
}