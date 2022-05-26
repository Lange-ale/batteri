package children;

import main.*;

public class morte extends Batterio {
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
    int id;
    private int muoviSuX; // 1 e 2 si, 0 no
    private boolean destra, su;
    private boolean first;

    public morte() {
        x = NextX;
        y = NextY;
        NextX += xDistanza;
        if (NextX >= Food.getWidth()) {
            NextY += yDistanza;
            NextX = xDistanza / 2;
        }
        id = nextId++;
        muoviSuX = id % 3;
        destra = id % 4 < 2;
        su = id % 4 == 0 || id % 4 == 3;
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

        if (muoviSuX== 0) y += su ? -1 : 1;
        else x += destra ? 1 : -1;
        muoviSuX = (muoviSuX+1)%3;

        if (x >= Food.getWidth() || x <= 0 || y >= Food.getHeight() || y <= 0){
            destra = x < Food.getWidth()/2;
            su = y > Food.getHeight()/2;
        }
    }

    @Override
    protected Batterio clone() throws CloneNotSupportedException {
        morte clone = (morte) super.clone();
        clone.id = id++;
        return clone;
    }
}