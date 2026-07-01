package tennisgame;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawBillboardPro;
import static com.raylib.Raylib.IsKeyDown;
import static com.raylib.Raylib.KEY_SPACE;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.SetTextureWrap;
import static com.raylib.Raylib.TEXTURE_WRAP_CLAMP;
import static com.raylib.Raylib.UnloadTexture;
import static com.raylib.Raylib.Vector2Normalize;
import static com.raylib.Raylib.Vector2Scale;
import static com.raylib.Raylib.Vector3Add;

import com.raylib.Raylib.BoundingBox;
import com.raylib.Raylib.Camera3D;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Texture;
import com.raylib.Raylib.Vector2;
import com.raylib.Raylib.Vector3;

/** Player */
public class Player {
	private Texture texture;
	private Vector3 pos;
	private float width;
	private float height;
	private static float baseSpeed = 3.0f;

	public Player() {
		texture = LoadTexture("src/main/assets/player.png");
		SetTextureWrap(texture, TEXTURE_WRAP_CLAMP);
		pos = new Vector3().x(10.0f).y(0.0f).z(0.0f);
		width = 0.7f;
		height = 1.8f;
	}

	public float getWidth() {
		return width;
	}

	public Vector3 getPos() {
		return pos;
	}

	public Vector3 getBallPos() {
		return new Vector3().x(pos.x()).y(1.0f + pos.y()).z(pos.z());
	}

	public float getBaseSpeed() {
		return baseSpeed;
	}

	public void move(Vector2 movementVect, float distance) {
		Vector2 scaledMovement = Vector2Scale(Vector2Normalize(movementVect), distance);
		pos.x(pos.x() + scaledMovement.x());
		pos.z(pos.z() + scaledMovement.y());
	}

	public BoundingBox getBoundingBox() {
		BoundingBox bb = new BoundingBox();
		bb.min(Vector3Add(pos, new Vector3().x(0.0f).y(0.0f).z(-width / 2.0f)));
		bb.max(Vector3Add(pos, new Vector3().x(0.0f).y(height).z(width / 2.0f)));
		return bb;
	}

	public float throwBallAngle(Vector2 mouseCourtPos) {
		float dx = mouseCourtPos.x() - pos.x();
		float dz = mouseCourtPos.y() - pos.z();
		return (float) Math.atan2(dz, dx);
	}

	public boolean askForService() {
		return IsKeyDown(KEY_SPACE);
	}

	public void draw(Camera3D cam) {
		DrawBillboardPro(
				cam,
				texture,
				new Rectangle().x(0).y(0).width(texture.width()).height(texture.height()), // source rect (whole image)
				Vector3Add(pos, new Vector3().x(0.0f).y(height / 2.0f).z(0.0f)),           // position (same as before)
				new Vector3().x(0.0f).y(1.0f).z(0.0f),                                     // up vector
				new Vector2().x(width).y(height),                                          // size on screen
				new Vector2().x(width / 2.0f).y(height / 2.0f),                            // origin (center it)
				0.0f,                                                                      // rotation
				WHITE
				);
	}

	public void unload() {
		UnloadTexture(texture);
	}
}
