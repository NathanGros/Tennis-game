package tennisgame;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawBillboardPro;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.SetTextureWrap;
import static com.raylib.Raylib.TEXTURE_WRAP_CLAMP;
import static com.raylib.Raylib.UnloadTexture;
import static com.raylib.Raylib.Vector3Add;

import com.raylib.Raylib.BoundingBox;
import com.raylib.Raylib.Camera3D;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Texture;
import com.raylib.Raylib.Vector2;
import com.raylib.Raylib.Vector3;

/** Bot */
public class Bot {
	private Texture texture;
	private Vector3 pos;
	private float width;
	private float height;
	private static float posMax = 10.973f / 2.0f;
	private static float posMin = -posMax;
	private static float baseSpeed = 3.0f;

	public Bot() {
		texture = LoadTexture("src/main/assets/bot.png");
		SetTextureWrap(texture, TEXTURE_WRAP_CLAMP);
		pos = new Vector3().x(-10.0f).y(0.0f).z(0.0f);
		width = 0.7f;
		height = 1.8f;
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

	public void moveSideways(float distance) {
		pos.z(pos.z() + distance);
		if (pos.z() < posMin) {
			pos.z(posMin);
			return;
		}
		if (pos.z() > posMax)
			pos.z(posMax);
	}

	public BoundingBox getBoundingBox() {
		BoundingBox bb = new BoundingBox();
		bb.min(Vector3Add(pos, new Vector3().x(0.0f).y(0.0f).z(-width / 2.0f)));
		bb.max(Vector3Add(pos, new Vector3().x(0.0f).y(height).z(width / 2.0f)));
		return bb;
	}

	public float throwBallAngle() {
		return (float) Math.random() * 0.4f - 0.2f;
	}

	public boolean askForService() {
		return true;
	}

	public void draw(Camera3D cam) {
		DrawBillboardPro(
				cam,
				texture,
				new Rectangle().x(0).y(0).width(texture.width()).height(texture.height()),
				Vector3Add(pos, new Vector3().x(0.0f).y(height / 2.0f).z(0.0f)),
				new Vector3().x(0.0f).y(1.0f).z(0.0f),
				new Vector2().x(width).y(height),
				new Vector2().x(width / 2.0f).y(height / 2.0f),
				0.0f,
				WHITE
		);
	}

	public void unload() {
		UnloadTexture(texture);
	}
}
