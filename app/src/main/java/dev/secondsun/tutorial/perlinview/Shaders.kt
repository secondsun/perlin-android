package dev.secondsun.tutorial.perlinview

val vertexShaderCode = "#version 300 es\n" +
    "in vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}"


val fragmentShaderCode ="#version 300 es\n" +
        "precision mediump float;\n" +
        "uniform float z;\n" +
        "layout(location = 0) out vec4 diffuseColor;\n" +
        "const int p[] = int[](\n" +
        "                        30, 35, 180, 97, 57, 196, 93, 6, 169, 37, 118, 54, 132, 53, 236, 240, 179, 147, 109, 7, 85,\n" +
        "                        64, 200, 12, 66, 79, 107, 158, 181, 186, 174, 36, 45, 198, 231, 9, 49, 115, 187, 211, 32, 26,\n" +
        "                        140, 176, 106, 0, 222, 173, 249, 48, 125, 51, 33, 23, 161, 144, 225, 95, 189, 50, 252, 193,\n" +
        "                        142, 199, 254, 116, 239, 98, 17, 190, 151, 39, 10, 76, 205, 58, 63, 136, 165, 150, 84, 182,\n" +
        "                        90, 21, 171, 168, 47, 133, 256, 103, 126, 138, 131, 42, 160, 19, 31, 245, 175, 104, 28, 91,\n" +
        "                        92, 195, 34, 185, 210, 156, 243, 146, 65, 219, 221, 55, 145, 130, 96, 203, 11, 177, 233, 102,\n" +
        "                        241, 8, 228, 194, 227, 110, 251, 238, 52, 124, 230, 4, 163, 206, 2, 75, 149, 112, 155, 159,\n" +
        "                        229, 153, 137, 74, 234, 141, 5, 18, 3, 14, 68, 246, 16, 100, 41, 250, 70, 80, 119, 73, 43,\n" +
        "                        226, 22, 105, 117, 139, 191, 202, 122, 1, 220, 237, 223, 152, 215, 244, 60, 253, 113, 204,\n" +
        "                        248, 69, 13, 62, 59, 86, 71, 128, 25, 38, 89, 20, 44, 78, 154, 135, 24, 121, 188, 218, 40, 15,\n" +
        "                        217, 208, 108, 46, 178, 192, 166, 212, 201, 247, 170, 164, 207, 148, 27, 99, 81, 94, 77, 209,\n" +
        "                        101, 197, 216, 167, 214, 255, 61, 87, 120, 224, 129, 111, 183, 83, 82, 162, 172, 143, 123,\n" +
        "                        232, 134, 88, 56, 213, 235, 114, 184, 72, 29, 127, 67, 157, 30, 35, 180, 97, 57, 196, 93, 6,\n" +
        "                        169, 37, 118, 54, 132, 53, 236, 240, 179, 147, 109, 7, 85, 64, 200, 12, 66, 79, 107, 158, 181,\n" +
        "                        186, 174, 36, 45, 198, 231, 9, 49, 115, 187, 211, 32, 26, 140, 176, 106, 0, 222, 173, 249, 48,\n" +
        "                        125, 51, 33, 23, 161, 144, 225, 95, 189, 50, 252, 193, 142, 199, 254, 116, 239, 98, 17, 190,\n" +
        "                        151, 39, 10, 76, 205, 58, 63, 136, 165, 150, 84, 182, 90, 21, 171, 168, 47, 133, 256, 103,\n" +
        "                        126, 138, 131, 42, 160, 19, 31, 245, 175, 104, 28, 91, 92, 195, 34, 185, 210, 156, 243, 146,\n" +
        "                        65, 219, 221, 55, 145, 130, 96, 203, 11, 177, 233, 102, 241, 8, 228, 194, 227, 110, 251, 238,\n" +
        "                        52, 124, 230, 4, 163, 206, 2, 75, 149, 112, 155, 159, 229, 153, 137, 74, 234, 141, 5, 18, 3,\n" +
        "                        14, 68, 246, 16, 100, 41, 250, 70, 80, 119, 73, 43, 226, 22, 105, 117, 139, 191, 202, 122, 1,\n" +
        "                        220, 237, 223, 152, 215, 244, 60, 253, 113, 204, 248, 69, 13, 62, 59, 86, 71, 128, 25, 38, 89,\n" +
        "                        20, 44, 78, 154, 135, 24, 121, 188, 218, 40, 15, 217, 208, 108, 46, 178, 192, 166, 212, 201,\n" +
        "                        247, 170, 164, 207, 148, 27, 99, 81, 94, 77, 209, 101, 197, 216, 167, 214, 255, 61, 87, 120,\n" +
        "                        224, 129, 111, 183, 83, 82, 162, 172, 143, 123, 232, 134, 88, 56, 213, 235, 114, 184, 72, 29,\n" +
        "                        127, 67, 157);\n" +
        "\n" +
        "float fade(in float t) {\n" +
        "    return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);      // 6t^5 - 15t^4 + 10t^3\n" +
        "}\n" +
        "\n" +
        "\n" +
        "int inc(in int num) {\n" +
        "    num++;\n" +
        "    return num;\n" +
        "}\n" +
        "\n" +
        "float lerp(in float a,in float b,in float x) {\n" +
        "    return a + x * (b - a);\n" +
        "}\n" +
        "\n" +
        "float grad(in int hash, in float x,in float y,in float z) {\n" +
        "    int h = hash    & 15;\n" +
        "    float u = h < 8  ? x : y;\n" +
        "    float v;\n" +
        "\n" +
        "\n" +
        "    if (h < 4)\n" +
        "    {\n" +
        "        v = y;\n" +
        "    } else if (h == 12 || h == 14 )\n" +
        "    {\n" +
        "        v = x;\n" +
        "    } else\n" +
        "    {\n" +
        "        v = z;\n" +
        "    }\n" +
        "\n" +
        "    return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v\n" +
        "    : -v);\n" +
        "}\n" +
        "\n" +
        "\n" +
        "\n" +
        "void main()\n" +
        "{\n" +
        "float ecks = gl_FragCoord.x *.01;\n" +
        "float why = gl_FragCoord.y *.01;\n" +
        "    int xi = int(ecks  );\n" +
        "    int yi = int(why   );\n" +
        "    int zi = int(z);\n" +
        "\n" +
        "\n" +
        "    float xf = ecks - float(xi);\n" +
        "    float yf = why - float(yi);\n" +
        "    float zf = z - float(zi);\n" +
        "\n" +
        "    float u = fade(xf);\n" +
        "    float v = fade(yf);\n" +
        "    float w = fade(zf);\n" +
        "\n" +
        "    //clamps!\n" +
        "    xi &= 0x7F;\n" +
        "    yi &= 0x7F;\n" +
        "    zi &= 0x7F;\n" +
        "\n" +
        "\n" +
        "    int aaa, aba, aab, abb, baa, bba, bab, bbb;\n" +
        "    aaa = p[p[p[xi] + yi] + zi];\n" +
        "    aba = p[p[p[xi] + inc(yi)] + zi];\n" +
        "    aab = p[p[p[xi] + yi] + inc(zi)];\n" +
        "    abb = p[p[p[xi] + inc(yi)] + inc(zi)];\n" +
        "    baa = p[p[p[inc(xi)] + yi] + zi];\n" +
        "    bba = p[p[p[inc(xi)] + inc(yi)] + zi];\n" +
        "    bab = p[p[p[inc(xi)] + yi] + inc(zi)];\n" +
        "    bbb = p[p[p[inc(xi)] + inc(yi)] + inc(zi)];\n" +
        "\n" +
        "    float x1 = lerp(grad(aaa, xf, yf, zf),\n" +
        "    // The gradient function calculates the dot product between a pseudorandom\n" +
        "    grad(baa, xf - 1.0, yf, zf),\n" +
        "    // gradient vector and the vector from the input coordinate to the 8\n" +
        "    u);                    // surrounding points in its unit cube.\n" +
        "    float x2 = lerp(grad(aba, xf, yf - 1.0, zf),\n" +
        "    // This is all then lerped together as a sort of weighted average based on the faded (u,v,w)\n" +
        "    grad(bba, xf - 1.0, yf - 1.0, zf),        // values we made earlier.\n" +
        "    u);\n" +
        "    float y1 = lerp(x1, x2, v);\n" +
        "    \n" +
        "    \n" +
        "    x1 = lerp(grad(aab, xf, yf, zf - 1.0),\n" +
        "    grad(bab, xf - 1.0, yf, zf - 1.0),\n" +
        "    u);\n" +
        "    x2 = lerp(grad(abb, xf, yf - 1.0, zf - 1.0),\n" +
        "    grad(bbb, xf - 1.0, yf - 1.0, zf - 1.0),\n" +
        "    u);\n" +
        "    float y2 = lerp(x1, x2, v);\n" +
        "\n" +
        "    float res =  (lerp(y1, y2, w) + 1.0) / 2.0;\n" +
        "    vec3 outColor = vec3(0.0,0.0,0.0);\n" +
        "\n" +
        "    if (res >= .0 && res < .2) {\n" +
        "        outColor = vec3(1.0,1.0,1.0);\n" +
        "    } else if (res >= 0.2 && res < .4) {\n" +
        "        outColor = vec3(1.0,0.0,0.0);\n" +
        "    } else if (res >= 0.4 && res < .6) {\n" +
        "        outColor = vec3(0.0,1.0,0.0);\n" +
        "    } else if (res >= 0.6 && res < .8) {\n" +
        "        outColor = vec3(0.0,0.0,1.0);\n" +
        "    } else {\n" +
        "        outColor = vec3(0.0,0.0,0.0);\n" +
        "    }\n" +
        "    diffuseColor = vec4(outColor, 1.0);\n" +
        "}"