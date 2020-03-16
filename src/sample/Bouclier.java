package sample;

public class Bouclier extends Arme {
    private int resistance;

    public Bouclier(){
        this.resistance = 600;
    }
    public int getResistance() {
        return resistance;
    }

    public void diminuerResistance(int degats) {
        this.resistance -= degats;
    }
}
