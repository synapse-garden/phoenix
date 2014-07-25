varying vec4 vertColor;


void main( void ) {
    // vec2 resolution = vec2( 2560, 1600 ); //STAND-IN, NEED UNIFORM INPUT
    vec2 resolution = vec2( 1920, 1200 ); //STAND-IN, NEED UNIFORM INPUT
	vec2 position = ( gl_FragCoord.xy / resolution.xy );

	float cTest = 0.0;
	vec3 atmo = vec3(0.7, 0.9, 1.2);
	// vec3 atmo2 = atmo;

	// cTest = pow(1.0-position.y,4.0);
	atmo -= 0.6*pow(position.y,2.0);
	atmo -= vec3( pow(1.0-position.y,24.0), pow(1.0-position.y,10.0), pow(1.0-position.y,3.0))*1.3;
	atmo += 0.3*pow(1.0-position.y,4.0);

	gl_FragColor = vec4( atmo, 1.0 );
	// gl_FragColor = vec4( vec3( cTest, cTest, cTest ), 1.0 );
}
