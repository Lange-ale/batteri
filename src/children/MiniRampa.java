package children;

import main.*;

public class MiniRampa extends Batterio {
    private boolean goDown;
    private boolean goSide;
    private boolean muovix;
    int n = (int)(Math.random() * 10);
    public MiniRampa() {
        if (n % 2 == 0) {
            goDown = true;
            goSide = false;
        } else {
            goDown = false;
            goSide = true;
        }
        muovix = true;
    }

    @Override
    protected void move() {
            for(int i = -10; i <= 10; i+=1) {
                for (int j = -10; j <= 10; j+=1) {
                    if(Food.isFood(x + i, y + j)) {
                        x = x + i;
                        y = y + j;
                        return;
                    }
                }
            }

        if (muovix) {
            if (goDown) {
                y++;
                if (y > Food.getHeight()) {
                    goDown = false;
                    y = Food.getHeight() - 1;
                }
            } else {
                y--;
                if (y < 0) {
                    goDown = true;
                    y = 0;
                }
            }
        } else {
            if (goSide) {
                x++;
                if (x > Food.getHeight()) {
                    goSide = false;
                    x = Food.getHeight() - 1;
                }
            } else {
                x--;
                if (x < 0) {
                    goSide = true;
                    x = 0;
                }
            }
        }
        muovix = !muovix;
    }



        @Override
    public main.Batterio clone() throws CloneNotSupportedException {
        MiniRampa clone = (MiniRampa) super.clone();
        clone.goDown = !goDown;
        clone.goSide = !goSide;
        return clone;
    }
}