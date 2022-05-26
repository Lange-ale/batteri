package children;

import main.*;

public class Zlange extends Batterio {
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

    // mainForm.;
    //Food.:     1024, 640
    //Batterio.;

    public Zlange() {
        x = NextX;
        y = NextY;
        NextX += xDistanza;
        if (NextX >= Food.getWidth()) {
            NextY += yDistanza;
            NextX = xDistanza / 2;
        }
        //System.out.println(x + "  " + y);
        //System.out.println(nRighe + " " + yDistanza);
        id = nextId++;
        sensoOrario = true;
        muoviSuX = true;
        centro = new int[]{x, y + raggio};
    }

    void vaiIn(int x, int y) {
        this.x = x;
        this.y = y;
        centro[0] = x;
        centro[1] = y + raggio;
        if (centro[1] > Food.getHeight())
            centro[1] = y - raggio;

        //TODO aggiorna la mappa di dov'e' il cibo
    }

    @Override
    protected void move() throws Exception {
        if (id==0 || first) { first = false; return; } //2 istruzioni sprecate per il meme

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

    private double distDaO(int x, int y) {
        return Math.sqrt(Math.pow(x - centro[0], 2) + Math.pow(y - centro[1], 2));
    }

    @Override
    protected Batterio clone() throws CloneNotSupportedException {
        Zlange clone = (Zlange) super.clone();
        clone.sensoOrario = !clone.sensoOrario;
        if (centro[1] > y)
            clone.centro[1] = y - raggio;
        clone.id = nextId++;
        return clone;
    }
}