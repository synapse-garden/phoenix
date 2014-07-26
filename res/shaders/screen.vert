// varying vec4 vertColor;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
    // vertColor = vec4( gl_Position.x, gl_Position.y, gl_Position.z, gl_Position.w );
}
