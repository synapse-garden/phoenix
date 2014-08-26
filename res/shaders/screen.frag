#version 150 core
#extension GL_ARB_shader_subroutine : require

uniform ivec2 resolution;
uniform vec2 mouse;

uniform float time;

out vec4 outputColor;

subroutine vec4 fragment( );
subroutine uniform fragment fragmentProcess;

vec4 black = vec4( 0.0, 0.0, 0.0, 0.0 );

subroutine( fragment ) vec4 fsQuad( ) {
    vec2 position = ( gl_FragCoord.xy / resolution.xy );
    float ratio = float(resolution.x) / float(resolution.y);
    vec2 sunPos = mouse / resolution;
    //vec2 sunPos = vec2( 0.75, sin( time*0.4 )*0.45 + 0.43 );

    vec3 atmo = vec3( 0.7, 0.9, 1.2 );

	atmo -= 0.5*pow( position.y, 1.0 );
    atmo -= vec3( pow( 1.0-position.y, (32.0 - sunPos.y * 32.0) ), pow( 1.0-position.y, (12.0 - sunPos.y * 9.0) ), pow( 1.0 - position.y, ( 4.0-sunPos.y * 2.0 ) ) ) * ( 1.6 - sunPos.y * 1.6 );
   	atmo += sunPos.y*pow( 1.-position.y, 4.0 );
	atmo *= 0.3 + sunPos.y;

	float s = length( position*vec2( ratio, 1.0 )-sunPos*vec2( ratio, 1.0 ) );
	s = 1.0/s;
	vec3 sun = vec3( s*1.0, s*0.55, s*0.22 );
	atmo += atmo*sun*0.15;

	return vec4( atmo*( 1.0 - sunPos.y * 0.5 ), 1.0 );
}

subroutine( fragment ) vec4 world( ) {
    return black;
}

void main( ) {
    outputColor = fragmentProcess( );
}
