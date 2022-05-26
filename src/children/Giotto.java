package children;

import main.*;

public class Giotto extends Batterio {
    //inizializzazione dei 100 batteri
    private static int nextId = 0;
    private static final int bPerRiga = (int) Math.sqrt(mainForm.getNumeroBatteriIniziali()) +
            (Math.sqrt(mainForm.getNumeroBatteriIniziali()) == 0 ? 0 : 1);
    private static final int nRighe = mainForm.getNumeroBatteriIniziali() / bPerRiga +
            (mainForm.getNumeroBatteriIniziali() % bPerRiga == 0 ? 0 : 1);
    private static final int xDistanza = Food.getWidth() / bPerRiga, yDistanza = Food.getHeight() / (nRighe);
    private static int NextX = xDistanza / 2, NextY = yDistanza / 2;

    //proprieta'
    private static final int visione = 200;
    private double theta;
    private final int[] centro;
    private final static int raggio = 20;
    int id;
    private boolean first;

    public Giotto() {
        x = NextX;
        y = NextY;
        NextX += xDistanza;
        if (NextX >= Food.getWidth()) {
            NextY += yDistanza;
            NextX = xDistanza / 2;
        }
        id = nextId++;
        theta = Math.PI/2;
        centro = new int[]{x, y + raggio};
    }

    void vaiIn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void move() throws Exception {
        if (id==0 || first) {
            first = false;
            return;
        }
        int d = 1;
        int v = visione < getHealth() ? visione : getHealth() - 1;
        int guardaOgni = v / 20;
        if (guardaOgni==0) guardaOgni = 1;
        for (int i = 1; i < v; i += guardaOgni) {
            if (Food.isFood(x, y + i)) { vaiIn(x, y += i); return; }
            if (Food.isFood(x, y - i)) { vaiIn(x, y -= i); return; }
            if (Food.isFood(x + i, y)) { vaiIn(x + i, y); return; }
            if (Food.isFood(x - i, y)) { vaiIn(x - i, y); return; }
            if (Food.isFood(x + d, y + d)) { vaiIn(x + d, y + d); return; }
            if (Food.isFood(x - d, y - d)) { vaiIn(x - d, y - d); return; }
            if (Food.isFood(x + d, y - d)) { vaiIn(x + d, y - d); return; }
            if (Food.isFood(x - d, y + d)) { vaiIn(x - d, y + d); return; }
            d += guardaOgni/2;
        }


        //movimento a cerchio
        int nuovaX = centro[0] + (int) (raggio * Math.cos(theta));
        int nuovaY = centro[1] - (int) (raggio * Math.sin(theta));
        if (Math.abs(nuovaX - x) - Math.abs(nuovaY - y) >= 0)
            x += nuovaX > x ? 1 : -1;
        else
            y += nuovaY > y ? 1 : -1;
        if (distDa(nuovaX, nuovaY) < 2)
            theta += 0.2;
        if (theta == 2 * Math.PI) theta = 0;
        if (x <= 0) centro[0] = x + raggio + 10;
        if (x >= Food.getWidth()) centro[0] = x - raggio - 10;
        if (y <= 0) centro[1] = y + raggio + 10;
        if (y >= Food.getHeight()) centro[1] = y - raggio - 10;
    }

    private double distDa(int Px, int Py) {
        return Math.sqrt(Math.pow(x - Px, 2) + Math.pow(y - Py, 2));
    }

    @Override
    protected Batterio clone() throws CloneNotSupportedException {
        Giotto clone = (Giotto) super.clone();
        clone.id = id++;
        return clone;
    }
}