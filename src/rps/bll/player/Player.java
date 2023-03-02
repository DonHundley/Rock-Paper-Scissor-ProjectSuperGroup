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


    private String winQuote = "";

    private String lossQuote = "";

    private String tieQuote = "";



    private String botThoughts = "";

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
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


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {

        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        return Move.Rock;
    }
}
