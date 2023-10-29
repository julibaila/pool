package pool;

import java.awt.Color;

public class Bolas {
    protected int x;
    protected int y;
    protected int radio;
    private int dx; 
    private int dy;
    private boolean isMoving = false;
    private boolean isStatic = false;
	private Color color;

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }


    public Bolas(int x, int y, int radio, int dx, int dy, Color color) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public int getRadius() {
        return radio;
    }
    public Color getColor()
    {
    	return color;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
    public int getDy() {
        return dy;
    }
    public int getDx() {
        return dx;
    }
    public void setIsMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean getIsMoving() {
        return isMoving;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int newX, int newY) {
        x = newX;
        y = newY;
    }
    public void collideWithAnotherBall(Bolas otherBall) {
        // Calculate the relative direction and speed of the balls.
        double angle = Math.atan2(otherBall.y - y, otherBall.x - x);
        double speed1 = Math.sqrt(dx * dx + dy * dy);
        double speed2 = Math.sqrt(otherBall.dx * otherBall.dx + otherBall.dy * otherBall.dy);

        // Calculate the new directions and speeds.
        double newDx1 = -speed1 * Math.cos(angle);
        double newDy1 = -speed1 * Math.sin(angle);
        double newDx2 = speed2 * Math.cos(angle);
        double newDy2 = speed2 * Math.sin(angle);

        // Update the velocities of the balls.
        dx = (int) newDx1;
        dy = (int) newDy1;
        otherBall.dx = (int) newDx2;
        otherBall.dy = (int) newDy2;
    }

}
