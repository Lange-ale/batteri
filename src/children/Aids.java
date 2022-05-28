package children;

import main.Food;

public class Aids extends main.Batterio{
    private boolean goDown;
    private boolean goSide;
    private boolean didX = true;
    int n = (int)(Math.random() * 10);
    public Aids() {
        if (n % 2 == 0 && didX == true) {
            goDown = true;
            goSide = false;
            didX = false;
        } else {
            goDown = false;
            goSide = true;
            didX = true;
        }
    }

    @Override
    protected void move() {
        for(int i = -20; i <= 20; i+=2) {
            for (int j = -20; j <= 20; j+=2) {
                if(Food.isFood(x + i, y + j)) {
                    x = x + i;
                    y = y + j;
                    return;
                }
            }
        }

        /*
        Human immunodeficiency virus infection and acquired immunodeficiency syndrome (HIV/AIDS) is a spectrum of conditions caused by infection with the human immunodeficiency virus (HIV),[9][10][11] a retrovirus.[12] Following initial infection an individual may not notice any symptoms, or may experience a brief period of influenza-like illness.[4] Typically, this is followed by a prolonged incubation period with no symptoms.[5] If the infection progresses, it interferes more with the immune system, increasing the risk of developing common infections such as tuberculosis, as well as other opportunistic infections, and tumors which are otherwise rare in people who have normal immune function.[4] These late symptoms of infection are referred to as acquired immunodeficiency syndrome (AIDS).[5] This stage is often also associated with unintended weight loss.[5]

        HIV is spread primarily by unprotected sex (including anal and vaginal sex), contaminated blood transfusions, hypodermic needles, and from mother to child during pregnancy, delivery, or breastfeeding.[13] Some bodily fluids, such as saliva, sweat and tears, do not transmit the virus.[14] Oral sex has little to no risk of transmitting the virus.[15]

        Methods of prevention include safe sex, needle exchange programs, treating those who are infected, as well as both pre- and post-exposure prophylaxis.[4] Disease in a baby can often be prevented by giving both the mother and child antiretroviral medication.[4]
         */

        if (!didX) {
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
    }

    @Override
    public main.Batterio clone() throws CloneNotSupportedException {
        Aids clone = (Aids)super.clone();
        clone.goDown = !goDown;
        clone.goSide = !goSide;
        return clone;
    }
}
