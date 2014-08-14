#version 440 core

uniform mat4 camera;
in vec3 inPosition;

void main() {
    gl_Position = camera * vec4(inPosition, 1.0);
}
