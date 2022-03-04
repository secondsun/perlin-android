package dev.secondsun.tutorial.perlinview

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.nio.IntBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

var fragmentShader = 0;

class MainActivity : AppCompatActivity() {


    private lateinit var gLView: GLSurfaceView
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = PerlinSurfaceView(this)
        setContentView(gLView)
    }

}

class PerlinSurfaceView(context: Context): GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(3)

        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}

class MyGLRenderer : GLSurfaceView.Renderer {
    private var mProgram: Int = -1;
    private lateinit var mSquare: Square
    private var z = 0f;


    fun loadShader(type: Int, shaderCode: String): Int {

        // create a vertex shader type (GLES32.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES32.GL_FRAGMENT_SHADER)
        return GLES32.glCreateShader(type).also { shader ->

            // add the source code to the shader and compile it
            GLES32.glShaderSource(shader, shaderCode)
            GLES32.glCompileShader(shader)

            var isCompiled = IntBuffer.allocate(1);
            GLES32.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, isCompiled);
            if(isCompiled[0] == GLES20.GL_FALSE)
            {
                var maxLength =  IntBuffer.allocate(1);
                GLES32.glGetShaderiv(shader, GLES20.GL_INFO_LOG_LENGTH, maxLength);

                // The maxLength includes the NULL character

                var errorLog = GLES32.glGetShaderInfoLog(shader);
                System.out.println(errorLog)
                // Provide the infolog in whatever manor you deem best.
                // Exit with failure.
                GLES32.glDeleteShader(shader); // Don't leak the shader.

            }

        }
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES32.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        val vertexShader: Int = loadShader(GLES32.GL_VERTEX_SHADER, vertexShaderCode)
        fragmentShader = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentShaderCode)

        mSquare = Square()

        // create empty OpenGL ES Program
        mProgram = GLES32.glCreateProgram().also {

            // add the vertex shader to program
            GLES32.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES32.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES32.glLinkProgram(it)
        }

    }

    private val vertexCount: Int = squareCoords.size / COORDS_PER_VERTEX
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex
    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0
    var lastTime: Long = System.currentTimeMillis()
    var frames = 0

    fun draw() {
        val currentTime: Long = System.currentTimeMillis()

        // Add program to OpenGL ES environment
        GLES32.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        var zLocation = GLES32.glGetUniformLocation(mProgram, "z")
        z += .001f;
        GLES32.glUniform1f(zLocation, z)

        // get handle to vertex shader's vPosition member
        positionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition").also {

            // Enable a handle to the triangle vertices
            GLES32.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES32.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES32.GL_FLOAT,
                false,
                vertexStride,
                mSquare.vertexBuffer
            )
            // Draw the triangle
            GLES32.glDrawArrays(GLES32.GL_TRIANGLE_FAN, 0, vertexCount)
            var error = GLES32.glGetError()
            if (error != 0) {
                var errorLog = GLES32.glGetProgramInfoLog(mProgram);
                System.out.println(errorLog)
            }
            // Disable vertex array
            GLES32.glDisableVertexAttribArray(it)
            error = GLES32.glGetError()
            if (error != 0) {
                var errorLog = GLES32.glGetShaderInfoLog(fragmentShader);
                System.out.println(errorLog)
            }
        }
        frames++;
        if ( currentTime - lastTime >= 1000 ){ // If last prinf() was more than 1 sec ago
            // printf and reset timer
            System.out.println(String.format("%d frames per second\n", frames));
            frames = 0;
            lastTime += 1000;
        }


    }


    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT)
        draw()
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES32.glViewport(0, 0, width, height)
    }
}
