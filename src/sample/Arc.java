package sample;

public class Arc extends Arme {

    private int degatsParFleche;
    private int nbFleches ;

    public Arc(int a , int b) {
        this.degatsParFleche = a;
        this.nbFleches = b ;
    }

    public int getNbFleches() {
        return nbFleches;
    }

    public void decrementNbFleches() {
        this.nbFleches -= 1;
    }

    public int getDegatsParFleche() {
        return degatsParFleche;
    }


}
