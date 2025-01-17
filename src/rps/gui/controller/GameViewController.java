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

    @FXML
    private ProgressBar hpPlayer;
    @FXML
    private ProgressBar hpAI;


    @FXML
    private Button rpsButton, quitButton, restartButton, playButton;

    @FXML
    private TextArea gameHistoryText, botThoughtsText, moveHistoryText;
    @FXML
    private ImageView imageAI;
    @FXML
    private Label labelPlayerName, labelAI, labelPlayerName2, roundLabel, winLossPlayer, botLevelDisplay, tiesLabel, winLossAI;

    private String playerName;

    private String botName;


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


    /**
     * We call this method from our initial view controller when the player is ready to play the game.
     * We take the chosen username and bot and use them for our game.
     * This method will also inform the player of the game state on launch through our text areas.
     */
    public void startGame() {
        moveHistoryText.setText("Welcome to the classic Rock, Paper and Scissor game! This is definitely how you remember always playing it." + "\n");
        human = new Player(playerName, PlayerType.Human);

        if (Objects.equals(botName, "PetRock")) {
            bot = new PetRock(PlayerType.AI);
            botLevelDisplay.setText("lv 1");
            imageAI.setImage(new Image(getClass().getResourceAsStream("/images/petrock_1.png")));
        } else if (Objects.equals(botName, "Roomba")) {
            bot = new Roomba(PlayerType.AI);
            botLevelDisplay.setText("Lv 2");
            imageAI.setImage(new Image(getClass().getResourceAsStream("/images/pngegg.png")));
        } else if (Objects.equals(botName, "Turing")) {
            bot = new Turing(PlayerType.AI);
            botLevelDisplay.setText("Lv 9001");
            imageAI.setImage(new Image(getClass().getResourceAsStream("/images/aituring.jpg")));
        } else {
            bot = new Bender(PlayerType.AI);
            imageAI.setImage(new Image(getClass().getResourceAsStream("/images/benderRPS.png")));
            botLevelDisplay.setText("Lv 3");
        }

        ge = new GameManager(human, bot);

        gameHistoryText.setText("The game is not yet over! Check here at the end.");
        moveHistoryText.setText(moveHistoryText.getText() + "Your opponent is " + bot.getPlayerName() + "\n" + "Starting game.... good luck!" + "\n");

        setStaticLabels();
        updateLabels();
    }


    public void playRock(ActionEvent actionEvent) {
        ge.playRound(Move.valueOf("Rock"));
        botTrashTalk();
        updateLabels();
    }

    public void playPaper(ActionEvent actionEvent) {
        ge.playRound(Move.valueOf("Paper"));
        botTrashTalk();
        updateLabels();
    }

    public void playScissors(ActionEvent actionEvent) {
        ge.playRound(Move.valueOf("Scissor"));
        botTrashTalk();
        updateLabels();
    }

    /**
     * When the user clicks the end button we use this method. First it tells the player how many rounds they won and what the total amount of rounds is.
     * Next it takes our result array and gives the user a break-down of each result in the game history text area.
     *
     * @param actionEvent
     */
    public void endGame(ActionEvent actionEvent) {
        endGameStats();
    }

    private void endGameStats(){
        int pWins = 0;
        if (ge.getGameState().getHistoricResults().size() > 0) {
            pWins = (int) ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerPlayer().getPlayerType() == PlayerType.Human).count();
            moveHistoryText.appendText("\n" + playerName + " won " + pWins + " out of " + ge.getGameState().getHistoricResults().size() + " rounds played.");
        }
        gameHistoryText.setText("");
        ge.getGameState().getHistoricResults().forEach(result -> gameHistoryText.appendText("\n" + getResultAsString(result)));
    }

    /**
     * This method updates the labels we use to display information to the player such as win/loss ratio, current round, and the number of ties.
     */
    private void updateLabels() {
        long botWins = ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerPlayer().getPlayerType() == PlayerType.AI).count();
        long playerWins = ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerPlayer().getPlayerType() == PlayerType.Human).count();
        roundLabel.setText("Round " + ge.getGameState().getRoundNumber());
        tiesLabel.setText("Ties " + ge.getGameState().getHistoricResults().stream().filter(result -> result.getType().equals(ResultType.Tie)).count());
        winLossAI.setText(botWins + " / " + playerWins);
        winLossPlayer.setText(playerWins + " / " + botWins);

        double maxBar = 10;
        hpPlayer.setProgress(1 - ((double) botWins/maxBar));
        hpAI.setProgress(1 -((double) playerWins/maxBar));
        isGameOver(botWins, playerWins);
    }

    private void setStaticLabels() {
        labelPlayerName.setText(playerName);
        labelPlayerName2.setText(playerName + " do?");

        labelAI.setText(bot.getPlayerName());
        roundLabel.setText("Round " + ge.getGameState().getRoundNumber());

    }

    /**
     * This method first adds the result to our move history text area and then fetches our bot taunt based on the round result.
     * We also use this to change our round counter for the player and to fill our bot thoughts text area.
     */
    public void botTrashTalk() {
        moveHistoryText.appendText("\n" + getResultAsString(ge.getResult()) + "\n");
        if (ge.getResult().getType().equals(ResultType.Tie)) {
            moveHistoryText.appendText("\n" + bot.getPlayerName() + ": " + bot.getTieQuote() + "\n");
        } else if (ge.getResult().getWinnerPlayer().equals(bot)) {
            moveHistoryText.appendText("\n" + bot.getPlayerName() + ": " + bot.getWinQuote() + "\n");
        } else moveHistoryText.appendText("\n" + bot.getPlayerName() + ": " + bot.getLossQuote() + "\n");
        botThoughtsText.appendText("\n" + bot.getBotThoughts());
    }

    private void isGameOver(long botWins, long playerWins){
        if(playerWins == 10){
            moveHistoryText.appendText("\n" + bot.getPlayerName() + " has run out of health! You win!");
            endGameStats();
        } else if (botWins == 10) {
            moveHistoryText.appendText("\n Oh no! " + playerName + " has run out of health! You lose!");
            endGameStats();
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
