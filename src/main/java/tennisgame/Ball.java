package tennisgame;

import static com.raylib.Raylib.*;

/** Ball */
public class Ball {
	private Model cube;
	private Vector3 pos;
	private float size;
	private static float posXMax = 23.77f / 2.0f;
	private static float posXMin = -posXMax;
	private static float posZMax = 10.973f / 2.0f;
	private static float posZMin = -posZMax;
	private static float baseSpeed = 10.0f;
	private Vector3 speedVect;

	public Ball() {
		cube = LoadModelFromMesh(GenMeshCube(1.0f, 1.0f, 1.0f));
		size = 0.2f;
		resetBall();
	}

	public float getBaseSpeed() {
		return baseSpeed;
	}

	public Vector3 getPos() {
		return new Vector3().x(pos.x()).y(pos.y()).z(pos.z());
	}

	public void setSpeedVect(Vector3 speedVect) {
		this.speedVect = speedVect;
	}

	public void resetBall() {
		pos = new Vector3().x(0.0f).y(1.0f).z(0.0f);
		speedVect = new Vector3().x(1.0f).y(0.0f).z(0.0f);
	}

	public void moveBall(float distance) {
		pos = Vector3Add(pos, Vector3Scale(Vector3Normalize(speedVect), distance));
	}

	public BoundingBox getBoundingBox() {
		BoundingBox bb = new BoundingBox();
		bb.min(Vector3Add(pos, Vector3Scale(Vector3Ones(), -1.0f * size / 2.0f)));
		bb.max(Vector3Add(pos, Vector3Scale(Vector3Ones(), size / 2.0f)));
		return bb;
	}

	public void draw() {
		DrawModelEx(
				cube,
				pos,
				new Vector3().x(0.0f).y(1.0f).z(0.0f),
				0.0f,
				new Vector3().x(size).y(size).z(size),
				new Color().r((byte) 210).g((byte) 229).b((byte) 54).a((byte) 255));
	}

	public void unload() {
		UnloadModel(cube);
	}
}
