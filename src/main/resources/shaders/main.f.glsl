#version 330

layout(location = 0) out vec4 fragcolor;

in vec2 tex_uv;
in vec3 norm;

uniform sampler2D texture;

void main() {
    float light = dot(norm, vec3(1, 1, 1) / 1.73);
    if (light < 0) light = 0;
    light = light * .8 + .2;
    vec4 color = texture2D(texture, vec2(tex_uv.x, 1 - tex_uv.y)).gbar;
    color.rgb *= light;
    fragcolor = color;
}
