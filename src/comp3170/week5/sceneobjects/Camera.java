package comp3170.week5.sceneobjects;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;

import comp3170.SceneObject;
import comp3170.InputManager;

public class Camera extends SceneObject {

	private float zoom = 20.0f;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	
	public Camera() {
		projectionMatrix = new Matrix4f().ortho(-zoom, zoom, -zoom, zoom, -1.0f, 1.0f);
	}
	
	public void resize(int w, int h) {
		float aspect = (float) w / (float) h;
		projectionMatrix = new Matrix4f().ortho(-zoom * aspect, zoom * aspect, -zoom, zoom, -1.0f, 1.0f);
	}
	
	public Matrix4f GetViewMatrix(Matrix4f dest) {
		viewMatrix = getMatrix();
		return viewMatrix.invert(dest);
	}
	
	public Matrix4f GetProjectionMatrix(Matrix4f dest) {
		return projectionMatrix.get(dest);
	}
	
	public void update(InputManager input, float deltaTime) {

		// zoom controls
		if (input.isKeyDown(GLFW_KEY_UP)) {
			zoom -= 10.0f * deltaTime;
			if (zoom < 1.0f) zoom = 1.0f;
		}
			
		if (input.isKeyDown(GLFW_KEY_DOWN)) {
			zoom += 10.0f * deltaTime;
		}

		// left/right movement
		if (input.isKeyDown(GLFW_KEY_LEFT)) {
			this.getMatrix().translate(-10.0f * deltaTime, 0.0f, 0.0f);
		}

		if (input.isKeyDown(GLFW_KEY_RIGHT)) {
			this.getMatrix().translate(10.0f * deltaTime, 0.0f, 0.0f);
		}

		// update projection after zoom change
		projectionMatrix = new Matrix4f().ortho(-zoom, zoom, -zoom, zoom, -1.0f, 1.0f);
	}
}