package rps.gui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rps.gui.controller.GameViewController;

import java.io.IOException;

public class InitialViewController {
    @FXML
   private TextField playerNameTextField;

    /**
     * method that opens the game when the play button is pressed. the player name and game level are registered*/
    public void clickPlayButton(ActionEvent actionEvent) throws IOException {
        if(!playerNameTextField.getText().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rps/gui/view/GameView.fxml"));
            Parent root = loader.load();

            GameViewController gameViewController = loader.getController();
            gameViewController.setPlayerName(playerNameTextField.getText());

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Rock-Paper-Scissors Game");
            stage.setScene(scene);
            stage.show();
            playerNameTextField.clear();
        }
    }


}
