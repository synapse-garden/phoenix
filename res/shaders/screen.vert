varying vec4 vertColor;

void main() {
    // gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
    vertColor = vec4(gl_Position.x/3.0, gl_Position.y/3.0, gl_Position.z/3.0, gl_Position.w/3.0);
}
