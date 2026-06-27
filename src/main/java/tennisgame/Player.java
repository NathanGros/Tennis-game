package tennisgame;

import static com.raylib.Raylib.*;

/** Player */
public class Player {
	private Model cube;
	private Vector3 pos;
	private float width;
	private float height;
	private static float posMax = 10.973f / 2.0f;
	private static float posMin = -posMax;
	private static float baseSpeed = 3.0f;

	public Player() {
		cube = LoadModelFromMesh(GenMeshCube(1.0f, 1.0f, 1.0f));
		pos = new Vector3().x(10.0f).y(0.0f).z(0.0f);
		width = 0.7f;
		height = 1.8f;
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
		float angle = (float) Math.atan(pos.z() / (10.0f + pos.x()));
		return -1.0f * (float) Math.PI + angle;
	}

	public void draw() {
		DrawModelEx(
				cube,
				Vector3Add(pos, new Vector3().x(0.0f).y(height / 2.0f).z(0.0f)),
				new Vector3().x(0.0f).y(1.0f).z(0.0f),
				0.0f,
				new Vector3().x(0.0f).y(height).z(width),
				new Color().r((byte) 224).g((byte) 157).b((byte) 174).a((byte) 255));
	}

	public void unload() {
		UnloadModel(cube);
	}
}
