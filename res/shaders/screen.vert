import com.sg.dg.util.Noise._
import com.sg.dg.util.Math._
varying vec4 vertColor;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
    vertColor = vec4(gl_Position.x, gl_Position.y, gl_Position.z, gl_Position.w); //0.6, 0.3, 0.4, 1.0);
}