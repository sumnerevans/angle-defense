#version 330

layout(location = 0) out vec4 output;

in vec2 tex_uv;

uniform sampler2D texture;

void main() {
    output = texture2D(texture, vec2(tex_uv.x, 1 - tex_uv.y)).gbar;
}
