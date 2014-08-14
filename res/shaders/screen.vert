#version 440 core

uniform mat4 camera;
in vec4 inPosition;

void main() {
    gl_Position = camera * inPosition;
}
