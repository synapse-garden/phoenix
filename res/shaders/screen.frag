#version 440 core

//// Structs

struct entity {
    vec3 position;
};

//// Uniforms

uniform ivec2 resolution;
uniform vec2 mouse;

uniform float time;

//// Out

out vec4 outputColor;

//// Constants

vec4 opaqueBlack = vec4( 0.0, 0.0, 0.0, 1.0 );
vec3 black = vec3( 0.0, 0.0, 0.0 );

//// Subroutines

subroutine vec4 layer( );
subroutine uniform layer drawLayer;

//// Function declarations

// drawing functions

vec3 drawShit( );
vec3 crosshairs( );
vec3 sky( );

//// Function implementations

void main( ) {
    outputColor = drawLayer( );
}

// subroutines
subroutine( layer ) vec4 drawFsq( ) {
    vec4 color = opaqueBlack;

    color.xyz += crosshairs( );

    return color;
}

subroutine( layer ) vec4 drawWorld( ) {
    vec4 color = opaqueBlack;

    // color.xyz += drawShit( );

    return color;
}

// drawing functions

vec3 crosshairs( ) {
	vec2 pos = ( gl_FragCoord.xy / resolution.xy );
	float aspect = float(resolution.x) / float(resolution.y);
	vec2 coord = (mouse / resolution);
	pos.x *= aspect;
	coord.x *= aspect;

	float v = 0.0;
	v += 0.001 / length( pos.x - coord.x );
	v += 0.001 / length( pos.y - coord.y );
	v += 0.001 / abs( 0.1 - length( pos - coord ) );

	vec3 color = v * vec3( 1.0, 0.2, 0.02 ) + vec3( 0.0, 0.0, 0.2 );
	return color;
}

vec3 sky( ) {
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

	return vec3( atmo*( 1.0 - sunPos.y * 0.5 ) );
}
