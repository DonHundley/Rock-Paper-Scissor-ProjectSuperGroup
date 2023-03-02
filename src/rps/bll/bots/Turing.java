package rps.bll.bots;

import rps.bll.game.*;
import rps.bll.player.*;

import java.util.*;

public class Turing  implements IPlayer {


    private String name = "The Turing Test";
    private PlayerType type;
    private String winQuote = "I calculated this victory.";

    private String lossQuote = "I have lost. This is an enigma.";

    private String tieQuote = "A tie? Curious.";
    private String botThoughts = "";

    /**
     *
     */
    public Turing(PlayerType type) {
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
    public String getBotThoughts() {
        return botThoughts;
    }

    @Override
    public PlayerType getPlayerType() {
        return type;
    }

    /**
     * Decides the next move for the bot...
     * We check the checkFirst method first and if it is null we check the checkSecond method, and finally if it is null we check the checkThird method.
     *
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        String str1 = "The Player is not cycling nor alternating moves. However, the ";
        String str2 = "Player is not spamming, cycling, or alternating moves. However, they have used ";
        String str3 = "Player is not spamming, cycling, alternating, or using a move in excess. My data shows ";
        String str4 = "The player seems to be cycling moves. If their order continues they will use ";
        String str5 = "The player is not cycling moves. However, the player seems to be alternating moves. If their pattern continues I predict their next move to be ";

        int sWins;
        int rWins;
        int pWins;

        int scissorPlays;
        int rockPlays;
        int paperPlays;

        Move mostRecent;
        Move secondToLast;
        Move thirdToLast;
        Move fourthToLast;

        Random rand = new Random();
        int r = rand.nextInt(3);

        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();
        sWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Scissor).count();
        rWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Rock).count();
        pWins = (int) results.stream().filter(result -> result.getType() == ResultType.Win).filter(result -> result.getWinnerMove() == Move.Paper).count();

        scissorPlays = (int) results.stream().filter(result -> result.getPlayerChoice() == Move.Scissor).count();
        rockPlays = (int) results.stream().filter(result -> result.getPlayerChoice() == Move.Rock).count();
        paperPlays = (int) results.stream().filter(result -> result.getPlayerChoice() == Move.Paper).count();

        int rounds = results.size();
        int roundsPlayed = results.size();


        if (roundsPlayed <= 3) {
            botThoughts = "I am learning.";
            if (r == 2) {
                return Move.Scissor;
            } else if (r == 1) {
                return Move.Rock;
            } else return Move.Paper;
        }


        mostRecent = results.get(rounds - 1).getPlayerChoice();
        secondToLast = results.get(rounds - 2).getPlayerChoice();
        thirdToLast = results.get(rounds - 3).getPlayerChoice();
        fourthToLast = results.get(rounds - 4).getPlayerChoice();

        if (!mostRecent.equals(secondToLast) && !secondToLast.equals(thirdToLast) && !thirdToLast.equals(mostRecent)) {
            if (thirdToLast.equals(Move.Rock)) {
                botThoughts = str4 + "Rock. I use Paper. \n";
                return Move.Paper;
            } else if (thirdToLast.equals(Move.Scissor)) {
                botThoughts = str4 + "Scissor. I will use Rock. \n";
                return Move.Rock;
            } else botThoughts = str4 + "Paper. I will use Scissor. \n";
            return Move.Scissor;
        } else if (mostRecent.equals(thirdToLast) && secondToLast.equals(fourthToLast)) {
            if (secondToLast.equals(Move.Paper)) {
                botThoughts = str5 + "Paper. So I will use Scissor. \n";
                return Move.Scissor;
            } else if (secondToLast.equals(Move.Rock)) {
                botThoughts = str5 + "Rock. So I will use Paper. \n";
                return Move.Paper;
            } else
                botThoughts = str5 + "Scissor. So I will use Rock. \n";
                return Move.Rock;
            } else if (mostRecent.equals(secondToLast) && secondToLast.equals(thirdToLast)) {
                if (mostRecent == Move.Scissor) {
                    botThoughts = str1 + "player is repeatedly using Scissor. I will use Rock. \n";
                    return Move.Rock;
                } else if (mostRecent == Move.Rock) {
                    botThoughts = str1 + "player is repeatedly using Rock. I will use Paper. \n";
                    return Move.Paper;
                } else botThoughts = str1 + "player is repeatedly using Paper. I will use Scissor. \n";
                return Move.Scissor;
            } else if ((double) scissorPlays / rounds >= .6) {
                botThoughts = str2 + "Scissor an excessive number of times. I choose Rock. \n";
                return Move.Rock;
            } else if ((double) rockPlays / rounds >= .6) {
                botThoughts = str2 + "Rock an excessive number of times. I choose Paper. \n";
                return Move.Paper;
            } else if ((double) paperPlays / rounds >= .6) {
                botThoughts = str2 + "Paper an excessive number of times. I choose Scissor. \n";
                return Move.Scissor;
            } else if (sWins >= rWins) {
                if (sWins > pWins) {
                    if (sWins > rockPlays) {
                        botThoughts = str3 + "Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds and the player has played rock less than the total amount of scissor wins. So I play Scissor. \n";
                        return Move.Scissor;
                    } else {
                        botThoughts = str3 + "Scissor has won the most times, with " + sWins + " out of " + rounds + " rounds and the player has played rock more than the total amount of scissor wins. So I play Paper. \n";
                        return Move.Paper;
                    }
                } else if (pWins > scissorPlays) {
                    botThoughts = str3 + "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played Scissor less than the total amount of paper wins. So I play Paper. \n";
                    return Move.Paper;
                } else
                    botThoughts = str3 + "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played Scissor more than the total amount of paper wins. So I play Rock. \n";
                return Move.Rock;
            } else if (rWins >= pWins) {
                if (rWins > paperPlays) {
                    botThoughts = str3 + "Rock has won the most times, with " + rWins + " out of " + rounds + " rounds and the player has played paper less than the total amount of rock wins. So I play Rock. \n";
                    return Move.Rock;
                } else {
                    botThoughts = str3 + "Rock has won the most times, with " + rWins + " out of " + rounds + " rounds and the player has played paper more than the total amount of rock wins. So I play Scissor. \n";
                    return Move.Scissor;
                }
            } else if (pWins > scissorPlays) {
                botThoughts = str3 + "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played scissor less than the total amount of paper wins. So I play Paper. \n";
                return Move.Paper;
            } else
                botThoughts = str3 + "Paper has won the most times, with " + pWins + " out of " + rounds + " rounds and the player has played scissor more than the total amount of paper wins. So I play Rock. \n";
        return Move.Rock;
    }
}
