package rps.gui.controller;

// Java imports

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rps.bll.bots.*;
import rps.bll.game.*;
import rps.bll.player.*;
import rps.gui.model.*;

import java.net.URL;
import java.util.*;

/**
 * @author smsj
 */
public class GameViewController implements Initializable {

    public Label tiesLabel;
    public ProgressBar hpPlayer;
    public Label winLossPlayer;
    public Label botLevelDisplay;
    public ProgressBar hpAI;
    public Label winLossAI;
    public Button playButton;
    public Button restartButton;
    public Button quitButton;
    public Button rpsButton;
    public TextArea moveHistoryText;
    @FXML
    private ImageView imageAI;
    @FXML
    private Label labelPlayerName, labelAI, labelPlayerName2, roundLabel;

    private String playerName;
    private GameState gs=new GameState();


    private Model model;
    private IPlayer human;
    private IPlayer bot;
    private GameManager ge;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void startGame(){
        moveHistoryText.setText("Welcome to the classic Rock, Paper and Scissor game! This is definitely how you remember always playing it." + "\n");
        human = new Player(playerName, PlayerType.Human);
        bot = new Bender(PlayerType.AI);
        ge = new GameManager(human,bot);
        //IPlayer bot = new Roomba(PlayerType.AI);
        moveHistoryText.setText(moveHistoryText.getText()+"Your opponent is " + bot.getPlayerName() + "\n" + "Starting game.... good luck!" +"\n");

        labelPlayerName.setText(playerName);
        labelPlayerName2.setText(playerName  + " do?");
        imageAI.setImage(new Image(getClass().getResourceAsStream("/images/benderRPS.png")));
        roundLabel.setText("Round " + String.valueOf(gs.getRoundNumber()));
    }
    public void setPlayerName(String playerName) {this.playerName = playerName;}

    public void setModel(Model model) {this.model = model;}
    public void playRock(ActionEvent actionEvent) {
        ge.playRound(Move.valueOf("Rock"));
        ge.getGameState().getHistoricResults().forEach((result) -> {
            moveHistoryText.setText(moveHistoryText.getText()+"\n"+ getResultAsString(result) + "\n");
        });}

    public void playPaper(ActionEvent actionEvent) {
        ge.playRound(Move.valueOf("Paper"));
        ge.getGameState().getHistoricResults().forEach((result) -> {
            moveHistoryText.setText(moveHistoryText.getText()+"\n"+ getResultAsString(result) + "\n");
        });}

    public void playScissors(ActionEvent actionEvent) {ge.playRound(Move.valueOf("Scissor"));
        ge.getGameState().getHistoricResults().forEach((result) -> {
            moveHistoryText.setText(moveHistoryText.getText()+"\n"+ getResultAsString(result) + "\n");
        });}
    public void endGame(ActionEvent actionEvent) {
        int pWins = 0;
        if (ge.getGameState().getHistoricResults().size() > 0) {
            pWins = (int) ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerPlayer().getPlayerType() == PlayerType.Human).count();
            moveHistoryText.setText(moveHistoryText.getText() + playerName + " won " + pWins + " out of " + ge.getGameState().getHistoricResults().size() + " rounds played.");
        }
    }

    /**
     * Provides a custom formatted string representation of a Result
     *
     * @param result
     * @return
     */
    public String getResultAsString(Result result) {
        String statusText = result.getType() == ResultType.Win ? "wins over " : "ties ";

        return "Round #" + result.getRoundNumber() + ":" +
                result.getWinnerPlayer().getPlayerName() +
                " (" + result.getWinnerMove() + ") " +
                statusText + result.getLoserPlayer().getPlayerName() +
                " (" + result.getLoserMove() + ")!";
    }

}
