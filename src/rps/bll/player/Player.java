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
        scissorPlays = (int) results.stream().filter(result -> result.getPlayerChoice() == Move.Scissor).count();
        rockPlays = (int) results.stream().filter(result -> result.getPlayerChoice() == Move.Rock).count();
        paperPlays = (int) results.stream().filter(result -> result.getPlayerChoice() == Move.Paper).count();

        int rounds = results.size();

        if(rounds >= 3) {
            if (sWins >= rWins) {
                if (sWins > pWins) {
                    if(sWins > rockPlays){
                        System.out.println("Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds and the player has played rock less than the total amount of scissor wins. So I play Scissor.");
                        return Move.Scissor;} else {
                        System.out.println("Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds and the player has played rock more than the total amount of scissor wins. So I play Paper.");
                        return Move.Paper;}
                } else if(pWins > scissorPlays){
                    System.out.println("Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played Scissor less than the total amount of paper wins. So I play Paper.");
                    return Move.Paper;
                } else
                    System.out.println("Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played Scissor more than the total amount of paper wins. So I play Rock.");
                    return Move.Rock;
            } else if (rWins >= pWins) {
                if (rWins > paperPlays){
                    System.out.println("Rock has won the most times, with " + rWins + " out of " + rounds + " rounds and the player has played paper less than the total amount of rock wins. So I play Rock.");
                    return Move.Rock;} else {
                    System.out.println("Rock has won the most times, with " + rWins + " out of " + rounds + " rounds and the player has played paper more than the total amount of rock wins. So I play Scissor.");
                    return Move.Scissor;}
            } else if(pWins > scissorPlays){
                System.out.println("Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played scissor less than the total amount of paper wins. So I play Paper.");
                return Move.Paper;}
            else System.out.println("Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played scissor more than the total amount of paper wins. So I play Rock.");
                return Move.Rock;

        }else System.out.println("I currently have no win knowledge.");
        if(r == 2){return Move.Scissor;} else if (r == 1) {return Move.Rock;} else return Move.Paper;
    }
}
