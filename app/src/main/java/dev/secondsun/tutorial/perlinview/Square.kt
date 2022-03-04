package dev.secondsun.tutorial.perlinview

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

// number of coordinates per vertex in this array
const val COORDS_PER_VERTEX = 3
var squareCoords = floatArrayOf(
    -1.5f,  1.5f, 1.0f,      // top left
    -1.5f, -1.5f, 0.0f,      // bottom left
    1.5f, -1.5f, 0.0f,      // bottom right
    1.5f,  1.5f, 0.0f       // top right
)

class Square {
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices

    // initialize vertex byte buffer for shape coordinates
    val vertexBuffer: FloatBuffer =
        // (# of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(squareCoords.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(squareCoords)
                position(0)
            }
        }

    // initialize byte buffer for the draw list
    val drawListBuffer: ShortBuffer =
        // (# of coordinate values * 2 bytes per short)
        ByteBuffer.allocateDirect(drawOrder.size * 2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(drawOrder)
                position(0)
            }
        }

}