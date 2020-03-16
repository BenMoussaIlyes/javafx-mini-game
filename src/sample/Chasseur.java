package sample;

import java.util.ArrayList;

public class Chasseur extends Personnage {

    public Chasseur(int pointsDeVie , int mana , int specialAttackMana ,int specialAttackDammage ,int  manaRegeneration,int level,int degatsparFl , int nbFleches ){
        super( pointsDeVie ,  mana ,  specialAttackMana , specialAttackDammage, manaRegeneration,level);
        this.armes = new ArrayList<>(1);
        this.armes.add(new Arc(degatsparFl,nbFleches)) ;
    }
    @Override
    public void attackeClassique(Personnage adversaire) {
        Arc a = (Arc) this.armes.get(0);
        if (a.getNbFleches() > 0){
            adversaire.subireDegats(a.getDegatsParFleche());
            a.decrementNbFleches();
        }
    }



}
