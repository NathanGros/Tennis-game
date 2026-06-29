package tennisgame;

public class MatchWonException extends RuntimeException {
    public final PlayersEnum winner;

    public MatchWonException(PlayersEnum winner) {
        this.winner = winner;
    }
}
