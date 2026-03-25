package comp3170.week5.sceneobjects;

import static org.lwjgl.opengl.GL41.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;

	public FlowerHead(int nPetals, Vector3f colour) {
		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
		
		petalColour = colour;
		
		float innerRadius = 0.2f;
		float outerRadius = 0.4f;
		
		int nVertices = nPetals * 2;
		vertices = new Vector4f[nVertices];
		
		for (int i = 0; i < nVertices; i++) {
			float angle = (float)(2 * Math.PI * i / nVertices);
			
			float radius;
			if (i % 2 == 0) {
				radius = outerRadius;
			} else {
				radius = innerRadius;
			}
			
			float x = (float)(radius * Math.cos(angle));
			float y = (float)(radius * Math.sin(angle));
			
			vertices[i] = new Vector4f(x, y, 0, 1);
		}
		
		vertexBuffer = GLBuffers.createBuffer(vertices);
	}

	public void update(float dt) {
		float time = (float) System.currentTimeMillis() / 1000.0f;
		float scale = 1.0f + 0.2f * (float)Math.sin(time * 2.0f);

		this.getMatrix().identity();
		this.getMatrix().translate(0.0f, 1.0f, 0.0f);
		this.getMatrix().scale(scale);
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
		shader.setAttribute("a_position", vertexBuffer);
		shader.setUniform("u_colour", petalColour);
		
		glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.length);
	}
}