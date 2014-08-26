#version 150 core
#extension GL_ARB_shader_subroutine : require

uniform mat4 camera;
in vec3 inPosition;

subroutine vec4 cameraTransform( mat4 camera, vec4 vertex );
subroutine uniform cameraTransform vertexCamera;

subroutine( cameraTransform ) vec4 fsQuad( mat4 camera, vec4 vertex ) {
    return vertex;
}

subroutine( cameraTransform ) vec4 world( mat4 camera, vec4 vertex ) {
    return camera * vertex;
}

void main( ) {
    gl_Position = vertexCamera( camera, vec4( inPosition, 1.0 ) );
}
