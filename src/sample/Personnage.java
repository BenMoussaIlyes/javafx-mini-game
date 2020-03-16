package sample;

import java.util.ArrayList;

public abstract class Personnage {
    protected int pointsDeVie ;
    protected int mana ;
    protected int specialAttackMana ;
    protected ArrayList<Arme> armes ;
    protected int specialAttackDammage ;
    protected int manaRegeneration ;
    protected int level ;

    public Personnage(int pointsDeVie , int mana , int specialAttackMana ,int specialAttackDammage , int manaRegeneration ,int level){
        this.pointsDeVie = pointsDeVie ;
        this.mana = mana ;
        this.specialAttackMana = specialAttackMana ;
        this.specialAttackDammage = specialAttackDammage ;
        this.manaRegeneration = manaRegeneration ;
        this.level = level;
    }
    public abstract void attackeClassique(Personnage adversaire);

    public void attackPuissante(Personnage adversaire){
        // verifier si il peut executer une attaque speciale ou pas
        if (this.mana - this.specialAttackMana >= 0){
            adversaire.subireDegats(specialAttackDammage);
            this.mana -= this.specialAttackMana;
        }
    }
    public void subireDegats(int degats){
        // chance de 15% d'avoir un double degats
        if (Math.random() < 0.15){
            degats*=2;
        }
        this.pointsDeVie -= degats ;
    }
}
