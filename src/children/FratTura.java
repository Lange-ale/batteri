package children;

import main.*;

public class FratTura extends Batterio {
    //posizione iniziale dei 100 batteri
    private static int nextId = 0;
    private static final int bPerRiga = (int) Math.sqrt(mainForm.getNumeroBatteriIniziali()) +
            (Math.sqrt(mainForm.getNumeroBatteriIniziali()) == 0 ? 0 : 1);
    private static final int nRighe = mainForm.getNumeroBatteriIniziali() / bPerRiga +
            (mainForm.getNumeroBatteriIniziali() % bPerRiga == 0 ? 0 : 1);
    private static final int xDistanza = Food.getWidth() / bPerRiga, yDistanza = Food.getHeight() / (nRighe);
    private static int NextX = xDistanza / 2, NextY = yDistanza / 2;
    //proprieta'
    private static final int visione = 200;
    private boolean muoviSuX;
    private boolean sensoOrario;
    private final static int raggio = 40;
    private final int[] centro;
    private boolean first;
    private int id;

    public FratTura() {
        x = NextX;
        y = NextY;
        NextX += xDistanza;
        if (NextX >= Food.getWidth()) {
            NextY += yDistanza;
            NextX = xDistanza / 2;
        }
        id = nextId++;
        sensoOrario = true;
        muoviSuX = true;
        centro = new int[]{x, y + raggio};
    }

    @Override
    protected Batterio clone() throws CloneNotSupportedException {
        FratTura clone = (FratTura) super.clone();
        clone.sensoOrario = !clone.sensoOrario;
        if (centro[1] > y)
            clone.centro[1] = y - raggio;
        clone.id = nextId++;
        return clone;
    }

    @Override
    protected void move() throws Exception {
        if (id==0 || first) { x++; first = false; return; } //2 istruzioni sprecate per il meme

        int vicino = 50;
        if (mangiaXeCroce(5, 1, vicino)) return; //80 istruzioni circa
        int guardaOgni = visione / 12; //300/12=25 celle
        if (mangiaXeCroce(guardaOgni, vicino, vicino+visione)) return; //96 istruzioni circa
        if (mangia306090(guardaOgni, vicino, vicino+visione)) return; //96 istruzioni circa

        muovi();
    }

    private void muovi() {
        if (muoviSuX) {
            if (sensoOrario)
                x += y < centro[1] ? 1 : -1;
            else
                x += y < centro[1] ? -1 : +1;
        } else {
            if (sensoOrario)
                y += x < centro[0] ? -1 : +1;
            else
                y += x < centro[0] ? +1 : -1;
        }
        muoviSuX = !muoviSuX;
        if (x >= Food.getWidth() || x <= 0 || y >= Food.getHeight() || y <= 0) {
            sensoOrario = !sensoOrario;
            muoviSuX = !muoviSuX;
        }
    }

    void vaiIn(int x, int y) {
        this.x = x;
        this.y = y;
        centro[0] = x;
        centro[1] = y + raggio;
        if (centro[1] > Food.getHeight())
            centro[1] = y - raggio;
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