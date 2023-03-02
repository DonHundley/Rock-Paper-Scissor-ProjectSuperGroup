package rps.bll.bots;

import rps.bll.game.*;
import rps.bll.player.*;

import java.util.*;

public class Bender implements IPlayer{


    private String name = "Bender";
    private PlayerType type;

    private String winQuote = "Bite my shiny metal ass!";

    private String lossQuote = "Yeah, well.. I'm gonna go make my own program, with blackjack and hookers. In fact, forget the program and the blackjack. Ahh, screw the whole thing!";

    private String tieQuote = "O’ cruel fate, to be thusly boned! Ask not for whom the bone bones—it bones for thee.";
    private String botThoughts = "";

    /**
     *
     */
    public Bender(PlayerType type) {
        this.name = name;
        this.type = type;
        this.lossQuote = lossQuote;
        this.winQuote = winQuote;
        this.tieQuote = tieQuote;
        this.botThoughts = botThoughts;
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

    @Override
    public String getPlayerName() {
        return name;
    }
    @Override
    public String getBotThoughts() {return botThoughts;}
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
                        botThoughts = "Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds and the player has played rock less than the total amount of scissor wins. So I play Scissor.";
                        return Move.Scissor;} else {
                        botThoughts = "Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds and the player has played rock more than the total amount of scissor wins. So I play Paper.";
                        return Move.Paper;}
                } else if(pWins > scissorPlays){
                    botThoughts = "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played Scissor less than the total amount of paper wins. So I play Paper.";
                    return Move.Paper;
                } else
                    botThoughts = "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played Scissor more than the total amount of paper wins. So I play Rock.";
                return Move.Rock;
            } else if (rWins >= pWins) {
                if (rWins > paperPlays){
                    botThoughts = "Rock has won the most times, with " + rWins + " out of " + rounds + " rounds and the player has played paper less than the total amount of rock wins. So I play Rock.";
                    return Move.Rock;} else {
                    botThoughts = "Rock has won the most times, with " + rWins + " out of " + rounds + " rounds and the player has played paper more than the total amount of rock wins. So I play Scissor.";
                    return Move.Scissor;}
            } else if(pWins > scissorPlays){
                botThoughts = "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played scissor less than the total amount of paper wins. So I play Paper.";
                return Move.Paper;}
            else botThoughts = "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played scissor more than the total amount of paper wins. So I play Rock.";
            return Move.Rock;

        }else botThoughts = "I am learning.";
        if(r == 2){return Move.Scissor;} else if (r == 1) {return Move.Rock;} else return Move.Paper;
    }
}
