#version 440 core

uniform mat4 camera;
in vec3 inPosition;

// subroutine FuncReturnType WorldPart(Type0 param0, Type1 param1, ...);

// subroutine(SubroutineTypeName) FuncReturnType FunctionName(Type0 param0, Type1 param1, ...);

void main() {
    gl_Position = camera * vec4(inPosition, 1.0);
}

