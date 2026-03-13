#version 410

uniform vec3 u_colour;	// RGB

layout(location = 0) out vec4 o_colour; 	// RGBA

void main() {
    o_colour = vec4(u_colour,1);
}

