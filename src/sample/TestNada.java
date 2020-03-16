package sample;

import java.util.Scanner;

public class TestNada {
    public static void main(String [] args){
        Personnage var1;
        Personnage var2;
        Scanner sc = new Scanner(System.in);
        while(var1.pointsDeVie > 0 && var2.pointsDeVie >0){
            System.out.println("joueur 1 attaque (1 ou 2)");
            int choix  = sc.nextInt();
            if (choix == 1 ){
                var1.attackeClassique();
            }else {
                var2.attackPuissante();
            }
            System.out.println("joueur 2 attaque (1 ou 2)");
            choix  = sc.nextInt();
            if (choix == 1 ){
                var1.attackeClassique();
            }else {
                var2.attackPuissante();
            }
        }
    }
}
