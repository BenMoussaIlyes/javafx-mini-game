package sample;

import java.util.ArrayList;

public class Guerrier extends Personnage {

    public Guerrier(int pointsDeVie , int mana , int specialAttackMana ,int specialAttackDammage ,int  manaRegeneration,int level,int degatsEpee ){
        super( pointsDeVie ,  mana ,  specialAttackMana , specialAttackDammage  ,manaRegeneration,level);
        this.armes = new ArrayList<>(2);
        this.armes.add(new Epee(degatsEpee)) ;
        if (level > 1){ // ajouter un bouclier si son niveau est superieur a 1
            this.armes.add(new Bouclier());
        }
    }

    @Override
    public void attackeClassique(Personnage adversaire) {
        Epee e = (Epee) this.armes.get(0);
        adversaire.subireDegats(e.getDegats());
    }


    @Override
    public void subireDegats(int degats) {
        if(armes.size() > 1){        // si il possede un bouclier
            if (Math.random() < 0.15){ // chance de coup critique
                degats*=2;
            }
            Bouclier b = (Bouclier) this.armes.get(1);
            if (b.getResistance() > degats) { // absorber les degats par le bouclier et ne pas affecter les points de vie
                b.diminuerResistance ( degats );
            }
            else {
                super.subireDegats(degats - b.getResistance()); //absorber les degats , et prendre le reste des points de vie
                b.diminuerResistance (  b.getResistance() ); // detruire le bouclier : resistance  = 0
                System.out.println(degats - b.getResistance());
            }
        }
        else {
            super.subireDegats(degats);
        }

    }


}
