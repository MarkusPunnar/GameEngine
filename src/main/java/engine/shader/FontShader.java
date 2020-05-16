package engine.shader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontShader extends Shader {

    private static final String VERTEX_FILE = "shaders/fontVertexShader.glsl";
    private static final String FRAGMENT_FILE = "shaders/fontFragmentShader.glsl";

    private Map<String, List<Integer>> uniformLocations;

    public FontShader() throws IOException {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "aPosition");
        bindAttribute(1, "aTextureCoords");
    }

    @Override
    protected void initUniformLocations() {
        if (uniformLocations == null) {
            uniformLocations = new HashMap<>();
        }
        uniformLocations.put("colour", List.of(getUniformLocation("colour")));
        uniformLocations.put("translation", List.of(getUniformLocation("translation")));
    }

    @Override
    public Map<String, List<Integer>> getUniformLocations() {
        return uniformLocations;
    }
}
