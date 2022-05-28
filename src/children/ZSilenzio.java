package children;

import main.*;

public class ZSilenzio extends Batterio {
    //inizializzazione dei 100 batteri
    private static int nextId = 0;
    private static final int bPerRiga = (int) Math.sqrt(mainForm.getNumeroBatteriIniziali()) +
            (Math.sqrt(mainForm.getNumeroBatteriIniziali()) == 0 ? 0 : 1);
    private static final int nRighe = mainForm.getNumeroBatteriIniziali() / bPerRiga +
            (mainForm.getNumeroBatteriIniziali() % bPerRiga == 0 ? 0 : 1);
    private static final int xDistanza = Food.getWidth() / bPerRiga, yDistanza = Food.getHeight() / (nRighe);
    private static int NextX = xDistanza / 2, NextY = yDistanza / 2;

    //proprieta'
    private static final int visione = 300;
    private int id;
    private int muoviSuX; // 1 e 2 si, 0 no
    private boolean destra, su;
    private boolean first;

    public ZSilenzio() {
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

    @Override
    protected Batterio clone() throws CloneNotSupportedException {
        ZSilenzio clone = (ZSilenzio) super.clone();
        clone.id = id++;
        return clone;
    }

    @Override
    protected void move() throws Exception {
        if (id==0 || first) { first = false; return; } //2 istruzioni sprecate per il meme

        int vicino = 50;
        if (mangiaXeCroce(5, 1, vicino)) return; //80 istruzioni circa
        int guardaOgni = visione / 12; //300/12=25 celle
        if (mangiaXeCroce(guardaOgni, vicino, vicino+visione)) return; //96 istruzioni circa
        if (mangia306090(guardaOgni, vicino, vicino+visione)) return; //96 istruzioni circa

        muovi();
    }

    private void muovi() {
        if (muoviSuX== 0) y += su ? -1 : 1;
        else x += destra ? 1 : -1;
        muoviSuX = (muoviSuX+1)%3;

        if (x >= Food.getWidth() || x <= 0) {
            destra = x < Food.getWidth()/2;
            su = id % 4 == 0 || id % 4 == 3;
        }
        if (y >= Food.getHeight() || y <= 0){
            destra = id % 4 < 2;
            su = y > Food.getHeight()/2;
        }
    }

    void vaiIn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private boolean mangiaXeCroce(int guardaOgni, int distanzaPartenza, int visione) {
        visione = (int)(visione/2+visione/2*radice3) < getHealth() ? (int)(visione/2+visione/2*radice3) : getHealth() - 1;
        if (guardaOgni==0) guardaOgni = 1;
        int d = distanzaPartenza/2;
        for (int i = distanzaPartenza; i < visione; i += guardaOgni) {
            if (Food.isFood(x, y + i)) { vaiIn(x, y += i); return true; }
            if (Food.isFood(x, y - i)) { vaiIn(x, y -= i); return true; }
            if (Food.isFood(x + i, y)) { vaiIn(x + i, y); return true; }
            if (Food.isFood(x - i, y)) { vaiIn(x - i, y); return true; }
            if (Food.isFood(x + d, y + d)) { vaiIn(x + d, y + d); return true; }
            if (Food.isFood(x - d, y - d)) { vaiIn(x - d, y - d); return true; }
            if (Food.isFood(x + d, y - d)) { vaiIn(x + d, y - d); return true; }
            if (Food.isFood(x - d, y + d)) { vaiIn(x - d, y + d); return true; }
            d += guardaOgni/2;
        }
        return false;
    }

    private static final double radice3 = Math.sqrt(3);

    private boolean mangia306090(int guardaOgni, int distanzaPartenza, int visione) {
        visione = visione < getHealth() ? visione : getHealth() - 1;
        if (guardaOgni==0) guardaOgni = 1;
        int d = distanzaPartenza/2;
        for (int i = distanzaPartenza; i < visione; i += guardaOgni) {
            if (Food.isFood(x + d, (int)(y - d * radice3))) { vaiIn(x + d, (int)(y - d * radice3)); return true; }
            if (Food.isFood((int)(x + d * radice3), y - d)) { vaiIn((int)(x + d * radice3), y - d); return true; }
            if (Food.isFood((int)(x + d * radice3), y + d)) { vaiIn((int)(x + d * radice3), y + d); return true; }
            if (Food.isFood(x + d, (int)(y + d * radice3))) { vaiIn(x + d, (int)(y + d * radice3)); return true; }
            if (Food.isFood(x - d, (int)(y + d * radice3))) { vaiIn(x - d, (int)(y + d * radice3)); return true; }
            if (Food.isFood((int)(x - d * radice3), y + d)) { vaiIn((int)(x - d * radice3), y + d); return true; }
            if (Food.isFood((int)(x - d * radice3), y - d)) { vaiIn((int)(x - d * radice3), y - d); return true; }
            if (Food.isFood(x - d, (int)(y - d * radice3))) { vaiIn(x - d, (int)(y - d * radice3)); return true; }
            d += guardaOgni/2;
        }
        return false;
    }
}