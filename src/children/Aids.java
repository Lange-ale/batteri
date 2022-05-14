package children;

import main.Food;

public class Aids extends main.Batterio{
    private boolean goDown;
    private boolean goSide;
    int n = (int)(Math.random() * 10);
    public Aids() {
        if (n % 2 == 0) {
            goDown = true;
            goSide = false;
        } else {
            goDown = false;
            goSide = true;
        }
    }

    @Override
    protected void move() {
        if (goDown) { y++;
            if (y > Food.getHeight()) { goDown = false; y = Food.getHeight() - 1; }
        }
        else { y--;
            if (y < 0) {
                goDown = true;
                y = 0;
            }
        }

        if (goSide) { x++;
            if (x > Food.getHeight()) { goSide = false; x = Food.getHeight() - 1; }}
        else { x--;
            if (x < 0) {
                goSide = true;
                x = 0;
            }
        }
    }

    @Override
    public main.Batterio clone() throws CloneNotSupportedException {
        Aids clone = (Aids)super.clone();
        clone.goDown = !goDown;
        clone.goSide = !goSide;
        return clone;
    }
}
