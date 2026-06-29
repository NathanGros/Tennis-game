package tennisgame;

/**
 * PlayersEnum
 */
public enum PlayersEnum {
	PLAYER,
	BOT;

	@Override
	public String toString() {
		return switch (this) {
			case PLAYER -> "Player";
			case BOT -> "Bot";
		};
	}
}
