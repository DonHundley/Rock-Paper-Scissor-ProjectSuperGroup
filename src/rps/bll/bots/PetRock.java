package rps.bll.bots;

import rps.bll.game.*;
import rps.bll.player.*;

import java.util.*;

public class PetRock  implements IPlayer {


    private String name = "Dwayne 'The PetRock' Johnson";
    private PlayerType type;

    private String winQuote = "Success isn’t overnight. It’s when every day you get a little better than the day before. It all adds up";

    private String lossQuote = "If something stands between you and your success, MOVE IT! Never be denied.";

    private String tieQuote = "Can you smell what I'm cooking?";
    private String botThoughts = "Rock is love, Rock is life.";

    /**
     *
     */
    public PetRock(PlayerType type) {
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
       return Move.Rock;
    }
}
