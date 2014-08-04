// #version 440 core

uniform mat4 camera;

// in vec4 Vertex;

void main() {
    // gl_Position = camera * vec4( Vertex, 1 );
    // gl_Position = camera * gl_Vertex; // vec4( gl_Vertex, 1.0 );
    gl_Position = camera * gl_Vertex;
}
