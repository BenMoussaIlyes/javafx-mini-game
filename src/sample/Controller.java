package sample;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.image.Image;

import static java.lang.Integer.parseInt;

public class Controller implements Initializable {
    private int confirmTotal = 0 ;
    private HashMap<String , Personnage> savedUsers ;
    private static Personnage fighter1 ;
    private static Personnage fighter2 ;
    private static String fighterString1 ;
    private static String fighterString2 ;
   @FXML
    Label choice1;
   @FXML
    Label choice2;
   @FXML
    ImageView imageFighter1;
   @FXML
    ImageView imageFighter2;
   @FXML
   Button button_confirm1;
   @FXML
   Button button_confirm2;
   @FXML
   Button button_bothConfirmed;
    @FXML
    TextField usernameTextfield1;
    @FXML
    TextField usernameTextfield2;
    @FXML
    Label result1;
    @FXML
    Label result2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        savedUsers = new HashMap<>(10);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("database/savedUsers.txt"));
            String line = reader.readLine(); // charger le fichier des utilisateurs ligne par ligne
            while (line != null) {
                String[] t = line.split("#"); // decoupe la ligne en un tableau avec le delimiteur
                // le 2eme champs dans le tableau est le nom de la classe
                // donc selon la classe on utilisera le constructeur correspondant
                if(t[1].equals("Chasseur")){
                    Chasseur c = new Chasseur(parseInt(t[2]) , parseInt(t[3]) , parseInt(t[4]) , parseInt(t[5]) ,parseInt(t[6]),parseInt(t[7]),parseInt(t[8]) , parseInt(t[9]));
                    savedUsers.put(t[0],c); // le nouveau objet cree est ajoute a l'ArrayList
                }
                else if(t[1].equals("Guerrier")){
                    Guerrier g = new Guerrier(parseInt(t[2]) , parseInt(t[3]) , parseInt(t[4]) , parseInt(t[5]) ,parseInt(t[6]),parseInt(t[7]),parseInt(t[8]) );
                    savedUsers.put(t[0],g);
                }else {
                    Mage m = new Mage(parseInt(t[2]), parseInt(t[3]), parseInt(t[4]), parseInt(t[5]), parseInt(t[6]), parseInt(t[7]), parseInt(t[8]), parseInt(t[9]));
                    savedUsers.put(t[0], m);
                }
                line = reader.readLine(); // ligne suivante
            }
                reader.close();

                selectWarrior1(); // creer les personnages par default pour eviter les erreurs
                selectMage2();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String getFighterString2() {
        return fighterString2;
    }

    public static void setFighterString2(String fighterString2) {
        Controller.fighterString2 = fighterString2;
    }

    public static String getFighterString1() {
        return fighterString1;
    }

    public static void setFighterString1(String fighterString1) {
        Controller.fighterString1 = fighterString1;
    }

    public static Personnage getFighter2() {
        return fighter2;
    }

    public static void setFighter2(Personnage fighter2) {
        Controller.fighter2 = fighter2;
    }

    public static Personnage getFighter1() {
        return fighter1;
    }

    public static void setFighter1(Personnage fighter1) {
        Controller.fighter1 = fighter1;
    }

    // handler du bouton de creation du personnage du joueur a gauche
    public void selectHunter1() throws  FileNotFoundException {
       fighter1 = null;
       fighter1 = new Chasseur(1000 , 1000 , 300 , 300 ,30,1,100 , 20);
       Image image = new Image(new FileInputStream("images/archer.png"));
       imageFighter1.setImage(image);

      choice1.setText("Chasseur Niveau 1");
        fighterString1 = "Archer";
   }
   public void selectWarrior1() throws FileNotFoundException {
       fighter1 = null;
       fighter1 = new Guerrier(1000 , 1000 , 300 , 300 ,30,1,100 );
       Image image = new Image(new FileInputStream("images/warrior.png"));
       imageFighter1.setImage(image);
       choice1.setText( "Guerrier Niveau 1");
       fighterString1 = "Warrior";
   }
   public void selectMage1() throws FileNotFoundException {
       fighter1 = null;
       fighter1 = new Mage(1000 , 1500 , 400 , 400 ,60,1 , 100,150 );
       Image image = new Image(new FileInputStream("images/mage.png"));
       imageFighter1.setImage(image);
      choice1.setText( "Mage Niveau 1");
       fighterString1 = "Mage";
   }
    // handler du bouton de creation du personnage du joueur a droite
    public void selectHunter2() throws FileNotFoundException {
       fighter2 = null;
       fighter2 = new Chasseur(1000 , 1000 , 300 , 300 ,30,1,100 , 20);
       Image image = new Image(new FileInputStream("images/archer.png"));
       imageFighter2.setImage(image);
      choice2.setText( "Chasseur Niveau 1");
       fighterString2 = "Archer";
   }
   public void selectWarrior2() throws FileNotFoundException {
       fighter2 = null;
       fighter2 = new Guerrier(1000 , 1000 , 300 , 300 ,30,1,100 );
       Image image = new Image(new FileInputStream("images/warrior.png"));
       imageFighter2.setImage(image);
      choice2.setText( "Guerrier Niveau 1");
       fighterString2 ="Warrior";
   }
   public void selectMage2() throws FileNotFoundException {
       fighter2 = null;
       fighter2 = new Mage(1000 , 1500 , 400 , 400 ,60,1 , 100,150 );
       Image image = new Image(new FileInputStream("images/mage.png"));
       imageFighter2.setImage(image);
      choice2.setText("Mage Niveau 1");
       fighterString2 = "Mage";
   }
   // desactiver le bouton et afficher le bouton "commencer" si la condition est achevee
   public void confirm1(){
      confirmTotal +=1;
      button_confirm1.setDisable(true);
      if (confirmTotal == 2){
         button_bothConfirmed.setVisible(true);
      }
   }
   //charger la scene suivante si on appuie sur le bouton commencer
   public void startFight(ActionEvent event) throws IOException {

      Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();// recupperer le stage dont l'event a ete declanche
      Parent pane = FXMLLoader.load(
              getClass().getResource("inFight.fxml"));
       pane.getStylesheets().add(getClass().getResource("buttons.css").toString());
       Scene scene = new Scene( pane,primaryStage.getWidth(),primaryStage.getHeight());
      primaryStage.setScene(scene);
   }

    // desactiver le bouton et afficher le bouton "commencer" si la condition est achevee
    public void confirm2(){
      confirmTotal +=1;
      button_confirm2.setDisable(true);
      if (confirmTotal == 2){
         button_bothConfirmed.setVisible(true);
      }
   }

    public void uploadCharacter1() throws FileNotFoundException {
        String user = usernameTextfield1.getText(); // recuperer le username tape par le joueur
        if(savedUsers.containsKey(user)){ //verifier l'existance du username
            result1.setText("(Personnage Existe)");
            result1.setVisible(true);
            result1.setTextFill(Paint.valueOf("#00ff00"));
            Personnage p = savedUsers.get(user); // charger la valeur du cle de username
            fighter1 = null;
            // selon le nom de la classe creer le constructeur et afficher le combattant
            if (p.getClass().getName().equals("Chasseur") || p.getClass().getName().equals("sample.Chasseur")){
                Chasseur cha = (Chasseur) p;
                fighter1 = new Chasseur(cha.pointsDeVie , cha.mana , cha.specialAttackMana , cha.specialAttackDammage ,cha.manaRegeneration,cha.level,((Arc)cha.armes.get(0)).getDegatsParFleche()+50,((Arc)cha.armes.get(0)).getNbFleches() );
                Image image = new Image(new FileInputStream("images/archer.png"));
                imageFighter1.setImage(image);
                choice1.setText( "Chasseur Niveau "+cha.level);
                fighterString1 = "Archer";
            }

            else if (p.getClass().getName().equals("Guerrier")|| p.getClass().getName().equals("sample.Guerrier")){
                Guerrier gue = (Guerrier) p;
                fighter1 = new Guerrier(gue.pointsDeVie , gue.mana , gue.specialAttackMana , gue.specialAttackDammage ,gue.manaRegeneration,gue.level,10 );

                Image image = new Image(new FileInputStream("images/warrior.png"));
                imageFighter1.setImage(image);
                choice1.setText( "Guerrier Niveau "+gue.level);
                fighterString1 = "Warrior";
            }

            else {
                Mage ma = (Mage) p;

                fighter1 = new Mage(ma.pointsDeVie , ma.mana , ma.specialAttackMana , ma.specialAttackDammage ,ma.manaRegeneration,ma.level,ma.getNormalAttackMana() + 10*ma.level,ma.getnormalAttackDamage() );
                Image image = new Image(new FileInputStream("images/mage.png"));
                imageFighter1.setImage(image);
                choice1.setText( "Mage Niveau"+ma.level);
                fighterString1 = "Mage";
            }
        } else {
            result1.setText("(Username non existant)");
            result1.setVisible(true);
        }
    }
    public void uploadCharacter2() throws FileNotFoundException {
        String user = usernameTextfield2.getText();
        if(savedUsers.containsKey(user)){
            result2.setText("(Personnage Existe)");
            result2.setVisible(true);
            result2.setTextFill(Paint.valueOf("#00ff00"));
            Personnage p = savedUsers.get(user);
            fighter2 = null;
            if (p.getClass().getName().equals("Chasseur")|| p.getClass().getName().equals("sample.Chasseur")){
                Chasseur cha = (Chasseur) p;
                fighter2 = new Chasseur(cha.pointsDeVie , cha.mana , cha.specialAttackMana , cha.specialAttackDammage ,cha.manaRegeneration,cha.level,((Arc)cha.armes.get(0)).getDegatsParFleche()+50,((Arc)cha.armes.get(0)).getNbFleches() );
                Image image = new Image(new FileInputStream("images/archer.png"));
                imageFighter2.setImage(image);
                choice2.setText( "Chasseur Niveau "+cha.level);
                fighterString2 = "Archer";
            }

            else if (p.getClass().getName().equals("Guerrier")|| p.getClass().getName().equals("sample.Guerrier")){
                Guerrier gue = (Guerrier) p;
                fighter2 = new Guerrier(gue.pointsDeVie , gue.mana , gue.specialAttackMana , gue.specialAttackDammage ,gue.manaRegeneration,gue.level,10 );

                Image image = new Image(new FileInputStream("images/warrior.png"));
                imageFighter2.setImage(image);
                choice2.setText( "Guerrier Niveau "+gue.level);
                fighterString2 = "Warrior";
            }

            else {
                Mage ma = (Mage) p;

                fighter2 = new Mage(ma.pointsDeVie , ma.mana , ma.specialAttackMana , ma.specialAttackDammage ,ma.manaRegeneration,ma.level,ma.getNormalAttackMana(),ma.getnormalAttackDamage() );
                Image image = new Image(new FileInputStream("images/mage.png"));
                imageFighter2.setImage(image);
                choice2.setText( "Mage Niveau "+ma.level);
                fighterString2 = "Mage";
            }
        } else {
            result2.setText("(Username non existant)");
            result2.setVisible(true);
        }
    }
}