#version 330

layout(location = 0) in vec3 v_pos;
layout(location = 1) in vec3 v_norm;
layout(location = 2) in vec2 v_tex;

void main() {
    gl_Position = vec4(v_pos, 1);
}
