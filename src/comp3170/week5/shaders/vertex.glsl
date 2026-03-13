#version 410

in vec4 a_position;		// MODEL
uniform mat4 u_mvpMatrix;	// MODEL->WORLD

void main() {
	
	gl_Position = u_mvpMatrix * a_position;
}

