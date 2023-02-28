package rps.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import rps.bll.player.*;
import rps.bll.bots.*;
import rps.gui.controller.GameViewController;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class InitialViewController implements Initializable {
    @FXML
    private ComboBox botSelectorCombo;
    @FXML
    private TextField playerNameTextField;

    /**
     * method that opens the game when the play button is pressed. the player name and game level are registered*/
    public void clickPlayButton(ActionEvent actionEvent) throws IOException {
        if(!playerNameTextField.getText().isEmpty()) {
            String playerName = playerNameTextField.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rps/gui/view/GameView.fxml"));
            Parent root = loader.load();

            GameViewController gameViewController = loader.getController();
            gameViewController.setPlayerName(playerName);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Rock-Paper-Scissors Game");
            stage.setScene(scene);
            stage.show();
            playerNameTextField.clear();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
