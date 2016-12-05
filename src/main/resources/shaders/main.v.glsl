#version 330

layout(location = 0) in vec3 v_pos;
layout(location = 1) in vec3 v_norm;
layout(location = 2) in vec2 v_tex;

uniform vec4 map_size;
uniform vec2 vert_range;
uniform mat4 model_trans;

out vec2 tex_uv;

void main() {
    vec4 pos = model_trans * vec4(v_pos, 1);
    pos = vec4(pos.x, pos.z, pos.y, 1);
    pos.xy -= map_size.xy;
    pos.xy /= map_size.zw;
    pos.xy *= 2;
    pos.xy -= 1;
    pos.z = vert_range.y - pos.z;
    pos.z /= (vert_range.y - vert_range.x);

    tex_uv = v_tex;

    gl_Position = pos;
}
