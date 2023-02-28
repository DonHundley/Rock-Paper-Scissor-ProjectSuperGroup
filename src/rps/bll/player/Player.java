package rps.bll.player;

//Project imports
import rps.bll.game.*;

//Java imports
import java.util.*;
import java.util.random.*;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
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
        int scissorPlays;
        int rockPlays;
        int paperPlays;

        Random rand = new Random();
        int r= rand.nextInt(3);

        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();
        sWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Scissor).count();
        rWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Rock).count();
        pWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Paper).count();
        int rounds = results.size();

        if(rounds > 5) {
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
        }else System.out.println("I currently have no win knowledge.");
        if(r == 2){return Move.Scissor;} else if (r == 1) {return Move.Rock;} else return Move.Paper;
    }
}
