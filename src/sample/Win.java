package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class Win implements Initializable {

    private HashMap<String , Personnage> savedUsers ;

    @FXML
    Label healthLabel;
    @FXML
    Label manaLabel;
    @FXML
    Label specialAttackManaLabel;
    @FXML
    Label specialAttackLabel;
    @FXML
    Label manaRegenLabel;
    @FXML
    ImageView winner_image;
    @FXML
    Label winnerLabel ;
    @FXML
    Label pointsLeftLabel ;
    @FXML
    TextField username;
    @FXML
    Label result;
    Personnage winner;
    int pointsLeft = 100;

    String fighter;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        savedUsers = new HashMap<>(10);
        BufferedReader reader;
        try {
            //charger les utilisateurs des fichiers vers le HashMap
            reader = new BufferedReader(new FileReader("database/savedUsers.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] t = line.split("#");
                if(t[1].equals("Chasseur")){
                    Chasseur c = new Chasseur(parseInt(t[2]) , parseInt(t[3]) , parseInt(t[4]) , parseInt(t[5]) ,parseInt(t[6]),parseInt(t[7]),parseInt(t[8]) , parseInt(t[9]));
                    savedUsers.put(t[0],c);
                }
                else if(t[1].equals("Guerrier")){
                    Guerrier g = new Guerrier(parseInt(t[2]) , parseInt(t[3]) , parseInt(t[4]) , parseInt(t[5]) ,parseInt(t[6]),parseInt(t[7]),parseInt(t[8]) );
                    savedUsers.put(t[0],g);
                }else {
                    Mage m = new Mage(parseInt(t[2]), parseInt(t[3]), parseInt(t[4]), parseInt(t[5]), parseInt(t[6]), parseInt(t[7]), parseInt(t[8]), parseInt(t[9]));
                    savedUsers.put(t[0], m);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(InFight.getWinnner() == 1){
            fighter = Controller.getFighterString1();
            winner = Controller.getFighter1();

            winnerLabel.setText("Joueur 1 gagne!");
        }else {
            fighter = Controller.getFighterString2();
            winner = Controller.getFighter2();
            winnerLabel.setText("Joueur 2 gagne!");
        }
        winner.level+=1;
        String file ="images/archer.png" ;
        switch(fighter) {
            case "Warrior":
                file = "images/warrior.png" ;
                break;
            case "Mage":
                file = "images/mage.png" ;
                break;
        }

        try {
            Image image = new Image(new FileInputStream(file));
            winner_image.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        healthLabel.setText(String.valueOf(winner.pointsDeVie));
        manaLabel.setText(String.valueOf(winner.mana));
        specialAttackManaLabel.setText(String.valueOf(winner.specialAttackMana));
        specialAttackLabel.setText(String.valueOf(winner.specialAttackDammage));
        manaRegenLabel.setText(String.valueOf(winner.manaRegeneration));

    }
// diminiuer des points du total , et ajouter des points de sante
    public void addHealth(){
        if(pointsLeft>0) {
            winner.pointsDeVie += 10;
            healthLabel.setText(String.valueOf(winner.pointsDeVie));
            pointsLeft-=10;
            pointsLeftLabel.setText("Points restants : "+ pointsLeft);
        }
    }
    public void addMana(){
        if(pointsLeft>0)
          {  winner.mana +=10;
            manaLabel.setText(String.valueOf(winner.mana));
            pointsLeft-=10;
            pointsLeftLabel.setText("Points restants : "+ pointsLeft);
          }

    }
    public void addSAM(){
        if(pointsLeft>0 && winner.specialAttackMana>10  ) {
            winner.specialAttackMana -= 10;
            specialAttackManaLabel.setText(String.valueOf(winner.specialAttackMana));
            pointsLeft -= 10;
            pointsLeftLabel.setText("Points restants : " + pointsLeft);
        }
    }
    public void addSA(){
        if(pointsLeft>0) {
            winner.specialAttackDammage += 10;
            specialAttackLabel.setText(String.valueOf(winner.specialAttackDammage));
            pointsLeft -= 10;
            pointsLeftLabel.setText("Points restants : " + pointsLeft);
        }
    }

    public void addMR(){
        if(pointsLeft>0) {
            winner.manaRegeneration += 10;
            manaRegenLabel.setText(String.valueOf(winner.manaRegeneration));
            pointsLeft -= 10;
            pointsLeftLabel.setText("Points restants : " + pointsLeft);
        }
    }

    public void savePlayer() throws IOException {
        if(! username.getText().isEmpty()){
            if (username.getText().contains("#")){// verifier si le nom contient le delimiteur
                result.setTextFill(Paint.valueOf("#ff0000"));
                result.setText("username ne doit pas contenir '#'");
            }else{
                // ajouter au HashMap
               savedUsers.put(username.getText() , winner);
               // mettre tous le HashMap dans le fichier
               this.saveIntoFile();
               result.setTextFill(Paint.valueOf("#00ff00"));
               result.setText("Personnage sauvgarde");

            }
        }
    }
    public void saveIntoFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("database/savedUsers.txt"));
        //parcourir tous le HashMap et le mettre ligne par ligne dans le fichier
        for (Map.Entry<String, Personnage> entry : savedUsers.entrySet()) {
            String k = entry.getKey() ;
            Personnage v = entry.getValue();
            writer.write(k+"#");
            if(v.getClass().getName().equals("sample.Chasseur") || v.getClass().getName().equals("Chasseur")){
                Chasseur cha = (Chasseur) v;
                writer.write("Chasseur"+"#"+cha.pointsDeVie +"#"+ cha.mana +"#"+ cha.specialAttackMana+"#"+ cha.specialAttackDammage +"#"+cha.manaRegeneration+"#"+ cha.level+"#"+ ((Arc)cha.armes.get(0)).getDegatsParFleche() +"#"+ ((Arc)cha.armes.get(0)).getNbFleches() + "\n");
            }else if(v.getClass().getName().equals("sample.Guerrier")|| v.getClass().getName().equals("Guerrier")){
                Guerrier gue = (Guerrier) v;
                writer.write("Guerrier" +"#"+ gue.pointsDeVie +"#"+ gue.mana +"#"+ gue.specialAttackMana +"#"+ gue.specialAttackDammage +"#"+ gue.manaRegeneration +"#"+ gue.level +"#"+ 10 + "\n");
            }else {
                Mage ma = (Mage) v;
                writer.write("Mage" +"#"+ma.pointsDeVie +"#"+ ma.mana +"#"+ ma.specialAttackMana +"#"+ ma.specialAttackDammage +"#"+ ma.manaRegeneration +"#"+ ma.level +"#"+ ma.getNormalAttackMana() +"#"+ ma.getnormalAttackDamage() + "\n");
            }

        }

        writer.close();
    }
}
