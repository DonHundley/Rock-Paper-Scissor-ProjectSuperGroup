package rps.gui.controller;

// Java imports

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rps.bll.Bots.Bender;
import rps.bll.Bots.Roomba;
import rps.bll.game.GameManager;
import rps.bll.game.GameState;
import rps.bll.player.IPlayer;
import rps.gui.ConsoleApp;
import rps.gui.view.InitialViewController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author smsj
 */
public class GameViewController implements Initializable {

    @FXML
    private ImageView imageAI;
    @FXML
    private Label labelPlayerName, labelAI, labelPlayerName2, roundLabel;

    private String playerName;
    private GameState gs=new GameState();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelPlayerName.setText(playerName); //not working
        labelPlayerName2.setText(playerName); //not working
        imageAI.setImage(new Image(getClass().getResourceAsStream("/images/benderRPS.png")));
        roundLabel.setText("Round " + String.valueOf(gs.getRoundNumber()));

    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
