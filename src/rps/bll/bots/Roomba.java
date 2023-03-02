package rps.bll.bots;

import rps.bll.game.*;
import rps.bll.player.*;

import java.util.*;
public class Roomba implements IPlayer{


    private String name = "Roomba";
    private PlayerType type;

    private String imageFilePath = "/images/RoombaRPS.png";

    private String winQuote = "You have been defeated by a vacuum, good work.";

    private String lossQuote = "The Roomba continues vacuuming.. are you trying to play a game against a vacuum?";

    private String tieQuote = "You open the bin on the Roomba after your turn and inside you find... a necktie?";



    private String botThoughts = "";

    /**
     *
     */
    public Roomba(PlayerType type) {
        this.name = name;
        this.type = type;
        this.imageFilePath = imageFilePath;
        this.lossQuote = lossQuote;
        this.winQuote = winQuote;
        this.tieQuote = tieQuote;
        this.botThoughts = botThoughts;
    }

    @Override
    public String getImageFilePath() {
        return imageFilePath;
    }
    @Override
    public String getBotThoughts() {
        return botThoughts;
    }

    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }

    @Override
    public String getWinQuote() {
        return winQuote;
    }

    @Override
    public String getLossQuote() {
        return lossQuote;
    }

    @Override
    public String getTieQuote() {
        return tieQuote;
    }

    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        int sWins;
        int rWins;
        int pWins;


        Random rand = new Random();
        int r = rand.nextInt(3);

        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();
        sWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Scissor).count();
        rWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Rock).count();
        pWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Paper).count();
        int rounds = results.size();

        if (rounds > 5) {
            if (sWins > rWins) {
                if (sWins > pWins) {
                    System.out.println("Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds.");
                    return Move.Scissor;
                } else {
                    System.out.println("Paper has won the most times, with " + pWins + " out of " + rounds + " rounds.");
                    return Move.Paper;
                }
            } else if (rWins > pWins) {
                System.out.println("Rock has won the most times, with " + rWins + " out of " + rounds + " rounds.");
                return Move.Rock;
            } else {
                System.out.println("Paper has won the most times, with " + pWins + " out of " + rounds + " rounds.");
                return Move.Paper;
            }
        } else System.out.println("I currently have no win knowledge.");
        if (r == 2) {
            return Move.Scissor;
        } else if (r == 1) {
            return Move.Rock;
        } else return Move.Paper;
    }

    public String getName() {
        return name;
    }
}
