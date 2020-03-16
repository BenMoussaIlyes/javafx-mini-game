package sample;

import java.util.ArrayList;

public class Mage extends Personnage {
    private int normalAttackMana;
    private int normalAttackDamage;

    public int getNormalAttackMana() {
        return normalAttackMana;
    }
    public int getnormalAttackDamage() {
        return normalAttackDamage;
    }


    public Mage(int pointsDeVie , int mana , int specialAttackMana , int specialAttackDammage, int manaRegeneration, int level , int normalAttackMana , int normalAttackDamage ){
        super( pointsDeVie ,  mana ,  specialAttackMana , specialAttackDammage, manaRegeneration,level);
        this.normalAttackMana = normalAttackMana ;
        this.normalAttackDamage = normalAttackDamage ;
        this.armes = new ArrayList<>(1); // pour eviter les erreurs

    }
    @Override
    public void attackeClassique(Personnage adversaire) {
        if (this.mana - this.normalAttackMana >= 0){
            adversaire.subireDegats(normalAttackDamage);
            this.mana -= this.normalAttackMana;
        }
    }




}
