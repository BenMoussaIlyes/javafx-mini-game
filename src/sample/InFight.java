package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InFight implements Initializable {
    private int intialHealth1;
    private int intialHealth2;
    private int intialMana1;
    private int intialMana2;
    private int initialRes1;
    private int initialRes2;
    private static int winnner ;
    @FXML
    Label choice1;
    @FXML
    Label choice2;
    @FXML
    HBox controls1;
    @FXML
    HBox controls2;
    @FXML
    ProgressBar health1;
    @FXML
    ProgressBar health2;
    @FXML
    ProgressBar mana1;
    @FXML
    ProgressBar mana2;
    @FXML
    ProgressBar armor1;
    @FXML
    ProgressBar armor2;
    @FXML
    ImageView image_fighter1;
    @FXML
    ImageView image_fighter2;
    @FXML
    HBox hboxArmor1;
    @FXML
    HBox hboxArmor2 ;
    @FXML
    Label fightLog;
    public static int getWinnner() {
        return winnner;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice1.setText(Controller.getFighterString1());
        choice2.setText(Controller.getFighterString2());
        String file ="images/archer.png" ;
        // charger les images des personnages
        switch(Controller.getFighterString1()) {
            case "Warrior":
                file = "images/warrior.png" ;
                break;
            case "Mage":
                file = "images/mage.png" ;
                break;
        }
        try {
            Image image = new Image(new FileInputStream(file));
            image_fighter1.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        file ="images/archer.png" ;
        switch(Controller.getFighterString2()) {
            case "Warrior":
                file = "images/warrior.png" ;
                break;
            case "Mage":
                file = "images/mage.png" ;
                break;
        }
        try {
            Image image = new Image(new FileInputStream(file));
            image_fighter2.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // garder les valeurs initiales pour avoir la pourcentages de points restante
        this.intialHealth1 = Controller.getFighter1().pointsDeVie ;
        this.intialHealth2 = Controller.getFighter2().pointsDeVie ;
        this.intialMana1 = Controller.getFighter1().mana ;
        this.intialMana2 = Controller.getFighter2().mana ;
        if (Controller.getFighter1().armes.size() > 1){
            this.initialRes1 = ((Bouclier)Controller.getFighter1().armes.get(1)).getResistance();
            hboxArmor1.setVisible(true);
        }
        if (Controller.getFighter2().armes.size() > 1){
            this.initialRes2 = ((Bouclier)Controller.getFighter2().armes.get(1)).getResistance();
            hboxArmor2.setVisible(true);

        }
        try {
            this.checkGameStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeSkipTurn1() throws IOException {
        fightLog.setText("P1 passe.\n"+fightLog.getText());
        this.fighter2Starts();
        Controller.getFighter1().mana += Controller.getFighter1().manaRegeneration;
        Controller.getFighter2().mana += Controller.getFighter2().manaRegeneration;
        mana1.setProgress((float)Controller.getFighter1().mana /(float) this.intialMana1);
        mana2.setProgress((float)Controller.getFighter2().mana /(float) this.intialMana2);
    }
    public void executeSkipTurn2() throws IOException {
        fightLog.setText("P2 passe.\n"+fightLog.getText());
        this.fighter1Starts();
        Controller.getFighter1().mana += Controller.getFighter1().manaRegeneration;
        Controller.getFighter2().mana += Controller.getFighter2().manaRegeneration;
        mana1.setProgress((float)Controller.getFighter1().mana /(float) this.intialMana1);
        mana2.setProgress((float)Controller.getFighter2().mana /(float) this.intialMana2);
    }

    public void executeNormalAttack1() throws IOException {
        fightLog.setText("P1 Attaque normale\n"+fightLog.getText());
        Controller.getFighter1().attackeClassique(Controller.getFighter2());
        this.checkGameStatus();
    }
    public void executeNormalAttack2() throws IOException {
        fightLog.setText("P2 Attaque normale\n"+fightLog.getText());
        Controller.getFighter2().attackeClassique(Controller.getFighter1());
        this.checkGameStatus();
    }

    public void executeSpecialAttack1() throws IOException {
        fightLog.setText("P1 Attaque speciale\n"+fightLog.getText());
        Controller.getFighter1().attackPuissante(Controller.getFighter2());
        this.checkGameStatus();
    }
    public void executeSpecialAttack2() throws IOException {
        fightLog.setText("P2 Attaque speciale\n"+fightLog.getText());
        Controller.getFighter2().attackPuissante(Controller.getFighter1());
        this.checkGameStatus();
    }
     public void checkGameStatus() throws IOException {
         Controller.getFighter1().mana += Controller.getFighter1().manaRegeneration;
         Controller.getFighter2().mana += Controller.getFighter2().manaRegeneration;
        try{
            // si quelqu'un gagne , une exception est levee
            if(Controller.getFighter1().pointsDeVie <= 0 ){
                winnner = 2;
                throw new PlayerDeathException();
            }
            else if(Controller.getFighter2().pointsDeVie <= 0 ){
                winnner = 1;
                throw new PlayerDeathException();
            } else {
                health1.setProgress((float)Controller.getFighter1().pointsDeVie /(float) this.intialHealth1);
                health2.setProgress((float)Controller.getFighter2().pointsDeVie /(float) this.intialHealth2);
                mana1.setProgress((float)Controller.getFighter1().mana /(float) this.intialMana1);
                mana2.setProgress((float)Controller.getFighter2().mana /(float) this.intialMana2);
                if (Controller.getFighter1().armes.size() > 1){
                float val = (float)((Bouclier)Controller.getFighter1().armes.get(1)).getResistance()/(float)this.initialRes1;
                    armor1.setProgress(val );
                }
                if (Controller.getFighter2().armes.size() > 1){
                    float val = (float) ((Bouclier)Controller.getFighter2().armes.get(1)).getResistance() / (float)this.initialRes2;
                    armor2.setProgress(val );
                }
                // on choisi qui attaque le suivant
                if(Controller.getFighter1().pointsDeVie < Controller.getFighter2().pointsDeVie){
                    this.fighter1Starts();
                }
                    else if(Controller.getFighter1().pointsDeVie > Controller.getFighter2().pointsDeVie){
                    this.fighter2Starts();
                }
                    else if (Math.random() > 0.5 ){
                    this.fighter1Starts();
                    }
                    else{
                    this.fighter2Starts();
                }



            }
        }
        catch(PlayerDeathException e){
            // charger la scene suivante
            Controller.getFighter1().pointsDeVie = this.intialHealth1;
            Controller.getFighter1().mana = this.intialMana1;
            Controller.getFighter2().pointsDeVie = this.intialHealth2;
            Controller.getFighter2().mana = this.intialMana2;
            Stage primaryStage = (Stage) image_fighter2.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    getClass().getResource("win.fxml"));
            root.getStylesheets().add(getClass().getResource("buttons.css").toString());
            Scene scene = new Scene( root,primaryStage.getWidth(),primaryStage.getHeight());
            primaryStage.setScene(scene);
        }


     }
     //afficher et cacher les boutons pour attaquer
     public void fighter1Starts(){
         controls1.setVisible(true);
         controls2.setVisible(false);
     }
     public void fighter2Starts(){
         controls1.setVisible(false);
         controls2.setVisible(true);
     }

}
