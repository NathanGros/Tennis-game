package tennisgame;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawText;

/**
 * GameScoreManager
 */
public class ScoresManager {
	private int playerGameScore;
	private int playerSetScore;
	private int playerMatchScore;

	private int botGameScore;
	private int botSetScore;
	private int botMatchScore;

	private boolean isTieBreak;

	private PlayersEnum servingPlayer;
	private boolean isServingDeuce;

	public ScoresManager() {
		playerGameScore = 0;
		playerSetScore = 0;
		playerMatchScore = 0;

		botGameScore = 0;
		botSetScore = 0;
		botMatchScore = 0;

		isTieBreak = false;

		servingPlayer = PlayersEnum.PLAYER;
		isServingDeuce = true;
	}

	public PlayersEnum getServingPlayer() {
		return servingPlayer;
	}

	public boolean isServingDeuce() {
		return isServingDeuce;
	}

	private void triggerTieBreak() {
		isTieBreak = true;
	}

	private void switchServer() {
		servingPlayer = switch (servingPlayer) {
			case PLAYER -> PlayersEnum.BOT;
			default -> PlayersEnum.PLAYER;
		};
	}

	public void playerWinPoint() {
		playerGameScore++;

		isServingDeuce = !isServingDeuce;
		if (isTieBreak) {
			if ((playerGameScore + botGameScore) % 2 == 1)
				switchServer();
		}

		int scoreGoal;
		if (!isTieBreak)
			scoreGoal = 4;
		else
			scoreGoal = 7;
		if (playerGameScore >= scoreGoal && playerGameScore - botGameScore >= 2)
			playerWinGame();
	}

	public void botWinPoint() {
		botGameScore++;

		isServingDeuce = !isServingDeuce;
		if (isTieBreak) {
			if ((playerGameScore + botGameScore) % 2 == 1)
				switchServer();
		}

		int scoreGoal;
		if (!isTieBreak)
			scoreGoal = 4;
		else
			scoreGoal = 7;
		if (botGameScore >= scoreGoal && botGameScore - playerGameScore >= 2)
			botWinGame();
	}

	private void playerWinGame() {
		isServingDeuce = true;
		switchServer();

		isTieBreak = false;
		playerGameScore = 0;
		botGameScore = 0;
		playerSetScore++;
		if (playerSetScore >= 7) {
			playerWinSet();
			return;
		}
		if (playerSetScore >= 6) {
			if (playerSetScore - botSetScore >= 2) {
				playerWinSet();
				return;
			}
			if (botSetScore == 6) {
				triggerTieBreak();
			}
		}
	}

	private void botWinGame() {
		isServingDeuce = true;
		switchServer();

		isTieBreak = false;
		playerGameScore = 0;
		botGameScore = 0;
		botSetScore++;
		if (botSetScore >= 7) {
			botWinSet();
			return;
		}
		if (botSetScore >= 6) {
			if (botSetScore - playerSetScore >= 2) {
				botWinSet();
				return;
			}
			if (playerSetScore == 6) {
				triggerTieBreak();
			}
		}
	}

	private void playerWinSet() {
		playerSetScore = 0;
		botSetScore = 0;
		playerMatchScore++;
		if (playerMatchScore >= 2)
			playerWin();
	}

	private void botWinSet() {
		playerSetScore = 0;
		botSetScore = 0;
		botMatchScore++;
		if (botMatchScore >= 2)
			botWin();
	}

	private void playerWin() {
		playerMatchScore = 0;
		botMatchScore = 0;
		throw new MatchWonException(PlayersEnum.PLAYER);
	}

	private void botWin() {
		playerMatchScore = 0;
		botMatchScore = 0;
		throw new MatchWonException(PlayersEnum.BOT);
	}

	public String getPlayerGameScore() {
		if (isTieBreak)
			return "" + playerGameScore;
		if (playerGameScore >= 4) {
			if (botGameScore < playerGameScore)
				return "Ad";
			return "40";
		}
		return switch (playerGameScore) {
			case 0 -> "0";
			case 1 -> "15";
			case 2 -> "30";
			case 3 -> "40";
			default -> "Err";
		};
	}

	public String getBotGameScore() {
		if (isTieBreak)
			return "" + botGameScore;
		if (botGameScore >= 4) {
			if (playerGameScore < botGameScore)
				return "Ad";
			return "40";
		}
		return switch (botGameScore) {
			case 0 -> "0";
			case 1 -> "15";
			case 2 -> "30";
			case 3 -> "40";
			default -> "Err";
		};
	}

	public String getPlayerSetScore() {
		return "" + playerSetScore;
	}

	public String getBotSetScore() {
		return "" + botSetScore;
	}

	public String getPlayerMatchScore() {
		return "" + playerMatchScore;
	}

	public String getBotMatchScore() {
		return "" + botMatchScore;
	}

	public void draw() {
		DrawRectangle(10, 20, 200, 50, WHITE);
		DrawRectangle(10, 80, 200, 50, WHITE);
		DrawText(getBotMatchScore(), 15, 20, 50, BLACK);
		DrawText(getPlayerMatchScore(), 15, 80, 50, BLACK);
		DrawText(getBotSetScore(), 75, 20, 50, BLACK);
		DrawText(getPlayerSetScore(), 75, 80, 50, BLACK);
		DrawText(getBotGameScore(), 135, 20, 50, BLACK);
		DrawText(getPlayerGameScore(), 135, 80, 50, BLACK);

		DrawRectangle(10, 140, 200, 50, WHITE);
		DrawRectangle(10, 200, 100, 50, WHITE);
		DrawText(servingPlayer.toString(), 15, 140, 50, BLACK);
		DrawText(isServingDeuce ? "D" : "Ad", 15, 200, 50, BLACK);
	}
}
