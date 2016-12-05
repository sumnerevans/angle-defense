#version 330

layout(location = 0) out vec4 o_color;

in vec2 f_uv;
in vec3 f_norm;

uniform sampler2D u_color_tex;

void main() {
    float light = -dot(f_norm, vec3(1, 1, -2) / 2.449);
    if (light < 0) light = 0;
    light = light * .8 + .2;
    vec4 color = texture(u_color_tex, vec2(f_uv.x, 1 - f_uv.y)).gbar;
    color.rgb *= light;
    o_color = color;
}
