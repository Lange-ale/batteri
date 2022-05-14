package children;

import main.*;

public class Zlange extends Batterio {
    private final static Thread calcolatoreConvenienza = new Thread(new CalcolatoreConvenienza());
    private static boolean istanziato = false;
    private final static int pxMTot = 160;
    private static int[][] mTot;
    private final static int pxMParz = 40;
    private static int[][] mParz;

    boolean destra = true;

    // mainForm.;
    //Food.:
    //Batterio.;

    public Zlange() {
        if (!istanziato) {
            istanziato = true;
            mTot = new int[Food.getHeight() / pxMTot + 1][Food.getWidth() / pxMTot + 1];
            mParz = new int[Food.getHeight() / pxMParz + 1][Food.getWidth() / pxMParz + 1];
            Zlange.calcolatoreConvenienza.start();
        }
    }


    @Override
    protected void move() throws Exception {
        System.out.println(mParz[y / pxMParz][x / pxMParz]);
        if (mParz[y / pxMParz][x / pxMParz] > 0) {
            for (int i = 1; i < pxMParz; i++) {
                if (Food.isFood(x, y + i)) { y += i; return;}
                if (Food.isFood(x, y - i)) { y -= i; return;}
                if (Food.isFood(x + i, y)) { x += i; return;}
                if (Food.isFood(x - i, y)) { x -= i; return;}
                if (Food.isFood(x + i, y + i)) { x += i; y += i; return;}
                if (Food.isFood(x - i, y - i)) { x -= i; y -= i; return;}
                if (Food.isFood(x + i, y - i)) { x += i; y -= i; return;}
                if (Food.isFood(x - i, y + i)) { x -= i; y += i; return;}
            }
        }
        if(destra) x++;
        else x--;
        if(x>=Food.getWidth() || x<=0) destra = !destra;
    }

    private static class CalcolatoreConvenienza implements Runnable {
        public void run() {
            while (true) {
                int[][] nuovaParz = new int[mParz.length][mParz[0].length];
                for (int r = 0; r < Food.getHeight(); r++)
                    for (int c = 0; c < Food.getWidth(); c++)
                        nuovaParz[r / pxMParz][c / pxMParz] += Food.isFood(c, r) ? 1 : 0;
                int[][] nuovaTot = new int[mTot.length][mTot[0].length];
                int parzialiInTot = pxMTot / pxMParz;
                for (int r = 0; r < nuovaParz.length; r++)
                    for (int c = 0; c < nuovaParz[0].length; c++)
                        nuovaTot[r / parzialiInTot][c / parzialiInTot] += nuovaParz[r][c];
                synchronized (mTot) {
                    mTot = nuovaTot;
                }
                synchronized (mParz) {
                    mParz = nuovaParz;
                }
/*
                for (int r = 0; r < nuovaTot.length; r++)
                    System.out.println(Arrays.toString(nuovaTot[r]));
                System.out.println("-\n-\n-\n");

 */
            }
        }
    }
}
