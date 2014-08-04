#version 440 core

uniform mat4 camera;

in vec4 Vertex;

void main() {
    gl_Position = camera * vec4( Vertex, 1 );
}
