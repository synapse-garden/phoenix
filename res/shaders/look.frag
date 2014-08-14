#version 440 core

uniform ivec2 resolution;
// uniform vec2 mouse;
// uniform vec3 cameraPos;
uniform vec3 cameraRot;

#define PI 3.1415956
#define TWO_PI PI*2.0
#define RATIO resolution.y / resolution.x

out vec4 outColor;

void main( void ) {
	vec2 position = ( gl_FragCoord.xy / resolution.xy - 0.5);
	vec2 look = vec2( cameraRot.x, cameraRot.y );
	vec3 color = vec3(0.0);

	color.x = sin( (position.x-0.5)*RATIO + look.x)*0.5 + 0.5;
	color.y = sin( (position.x-0.5)*RATIO + look.x + PI/2.0)*0.5 + 0.5;
	color.z = position.y*0.25 + 0.5 + look.y*0.25;
	outColor = vec4( ceil(color*30.0)/30.0, 1.0 );
}