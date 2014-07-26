uniform vec2 resolution;
uniform vec2 mouse;
uniform float time;

void main( void ) {
	vec2 position = ( gl_FragCoord.xy / resolution.xy );
	//vec2 sunPos = mouse;
	vec2 sunPos = vec2( 0.75, sin(time*0.4)*0.45+0.43 );
	//vec2 sunPos = vec2( 0.75, 0.5 );

	vec3 atmo = vec3(0.7, 0.9, 1.2);

	atmo -= 0.6*pow(position.y,1.0);
	atmo -= vec3( pow(1.0-position.y,(32.0-sunPos.y*16.0)), pow(1.0-position.y,(12.0-sunPos.y*6.0)), pow(1.0-position.y,(4.0-mouse.y*2.0)))*(1.6-sunPos.y*1.6);
	atmo += sunPos.y*pow(1.-position.y,4.0);
	atmo *= 0.4+sunPos.y;

	float s = length(position*vec2(1.777,1.0)-sunPos*vec2(1.777,1.0));
	s = 20.0/s;
	atmo+=atmo*s*0.005 ;

	gl_FragColor = vec4( atmo*(1.0-sunPos.y*0.5), 1.0 );
}