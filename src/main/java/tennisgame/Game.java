package tennisgame;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

/** Game */
public class Game {
	private void DrawScene(Model cube) {
		DrawModelEx(
				cube,
				Vector3Zero(),
				new Vector3().x(0.0f).y(1.0f).z(0.0f),
				0.0f,
				new Vector3().x(23.77f).y(0.0f).z(10.973f),
				new Color().r((byte) 68).g((byte) 133).b((byte) 227).a((byte) 255));
		DrawModelEx(
				cube,
				new Vector3().x(0.0f).y(0.5f).z(0.0f),
				new Vector3().x(0.0f).y(1.0f).z(0.0f),
				0.0f,
				new Vector3().x(0.0f).y(1.0f).z(10.973f),
				WHITE);
	}

	private void launchWindow() {
		int screenWidth = 800;
		int screenHeight = 450;

		SetConfigFlags(FLAG_MSAA_4X_HINT);
		InitWindow(screenWidth, screenHeight, "Tennis game");
	}

	public void startGame() {
		launchWindow();

		Camera3D cam = new Camera3D()
				._position(new Vector3().x(20.0f).y(7.0f).z(0.0f))
				.target(new Vector3().x(4.0f).y(0.0f).z(0.0f))
				.projection(CAMERA_PERSPECTIVE)
				.up(new Vector3().x(0.0f).y(1.0f).z(0.0f))
				.fovy(40.0f);

		Model cube = LoadModelFromMesh(GenMeshCube(1.0f, 1.0f, 1.0f));
		Player player = new Player();
		Bot bot = new Bot();
		Ball ball = new Ball();

		SetTargetFPS(60);
		Color backgroundColor = new Color().r((byte) 60).g((byte) 143).b((byte) 146).a((byte) 255);

		while (!WindowShouldClose()) {
			// Update
			float dt = GetFrameTime();
			if (IsKeyDown(KEY_LEFT))
				player.moveSideways(dt * player.getBaseSpeed());
			if (IsKeyDown(KEY_RIGHT))
				player.moveSideways(-1.0f * dt * player.getBaseSpeed());
			ball.moveBall(dt * ball.getBaseSpeed());

			// Players collisions
			if (CheckCollisionBoxes(player.getBoundingBox(), ball.getBoundingBox())) {
				float newBallAngle = player.throwBallAngle();
				Vector3 newBallSpeedVect = new Vector3();
				newBallSpeedVect.x((float) Math.cos(newBallAngle));
				newBallSpeedVect.y(0.0f);
				newBallSpeedVect.z((float) Math.sin(newBallAngle));
				ball.setSpeedVect(newBallSpeedVect);
			}
			if (CheckCollisionBoxes(bot.getBoundingBox(), ball.getBoundingBox())) {
				float newBallAngle = bot.throwBallAngle();
				Vector3 newBallSpeedVect = new Vector3();
				newBallSpeedVect.x((float) Math.cos(newBallAngle));
				newBallSpeedVect.y(0.0f);
				newBallSpeedVect.z((float) Math.sin(newBallAngle));
				ball.setSpeedVect(newBallSpeedVect);
			}

			// Out of court
			Vector3 ballPos = ball.getPos();
			if (ballPos.x() < -11.885 || ballPos.x() > 11.885 || ballPos.z() < -5.4865 || ballPos.z() > 5.4865)
				ball.resetBall();

			// Draw
			BeginDrawing();
			ClearBackground(backgroundColor);
			BeginMode3D(cam);
			DrawScene(cube);
			player.draw();
			bot.draw();
			ball.draw();
			EndMode3D();
			EndDrawing();
		}
		// De-Initialization
		player.unload();
		bot.unload();
		ball.unload();
		UnloadModel(cube);
		CloseWindow();
	}
}
