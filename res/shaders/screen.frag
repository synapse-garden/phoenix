varying vec4 vertColor;

uniform vec2 resolution;
uniform vec2 mouse;
uniform float time;

// uniform float color;

void main() {
    gl_FragColor = vertColor;
    //gl_FragColor = vec4( color, color, color, color );
}