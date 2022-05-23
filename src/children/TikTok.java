package children;

import main.*;

public class TikTok extends Batterio {
    //posizione iniziale dei 100 batteri
    private static final int bPerRiga = (int) Math.sqrt(mainForm.getNumeroBatteriIniziali()) +
            (Math.sqrt(mainForm.getNumeroBatteriIniziali()) == 0 ? 0 : 1);
    private static final int nRighe = mainForm.getNumeroBatteriIniziali() / bPerRiga +
            (mainForm.getNumeroBatteriIniziali() % bPerRiga == 0 ? 0 : 1);
    private static final int xDistanza = Food.getWidth() / bPerRiga, yDistanza = Food.getHeight() / (nRighe);
    private static int NextX = xDistanza / 2, NextY = yDistanza / 2;
    //proprieta'
    private static final int visione = 300;
    private boolean muoviSuX;
    private boolean sensoOrario;
    private final static int raggio = 40;
    private final int[] centro;
    private int xx[][], yy[][];

    void creaGrigliaX(){
        for (int i = 0; i < 1024; i+=64) {
            for (int j = 0; j < 640; j+=40) {
                xx[i][j] = j;
            }
        }
    }

    void creaGrigliaY(){
        for (int i = 0; i < 1024; i+=64) {
            for (int j = 0; j < 640; j+=40) {
                yy[i][j] = i;
            }
        }
    }

    // mainForm.;
    //Food.:     1024, 640
    //Batterio.;

    public TikTok() {
        xx=new int[16][16];
        yy=new int[16][16];
        creaGrigliaX();
        creaGrigliaY();
        x = NextX;
        y = NextY;
        NextX += xDistanza;
        if (NextX >= Food.getWidth()) {
            NextY += yDistanza;
            NextX = xDistanza / 2;
        }
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
        System.out.println("Zlange: " + getHealth());
        int d = 1;
        int v = visione < getHealth() ? visione : getHealth() - 1;
        for (int i = 1; i < v; i += 4) {
            if (Food.isFood(x, y + i)) { vaiIn(x, y += i); return; }
            if (Food.isFood(x, y - i)) { vaiIn(x, y -= i); return; }
            if (Food.isFood(x + i, y)) { vaiIn(x + i, y); return; }
            if (Food.isFood(x - i, y)) { vaiIn(x - i, y); return; }
            if (Food.isFood(x + d, y + d)) { vaiIn(x + d, y + d); return; }
            if (Food.isFood(x - d, y - d)) { vaiIn(x - d, y - d); return; }
            if (Food.isFood(x + d, y - d)) { vaiIn(x + d, y - d); return; }
            if (Food.isFood(x - d, y + d)) { vaiIn(x - d, y + d); return; }

            d += 2;
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
        for (int i = 0; i < 1024; i+=64) {
            for (int j = 0; j < 640; j+=40) {
                if(x>i && x<i+64){x=xx[i][j];}
                if(y>j && y<j+64){y=yy[i][j];}
            }
        }
    }


    @Override
    protected Batterio clone() throws CloneNotSupportedException {
        TikTok clone = (TikTok) super.clone();
        clone.sensoOrario = !clone.sensoOrario;
        if (centro[1] > y)
            clone.centro[1] = y - raggio;
        return clone;
    }
}

