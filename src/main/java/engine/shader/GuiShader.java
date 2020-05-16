package engine.shader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiShader extends Shader {

    private static final String VERTEX_FILE = "shaders/guiVertexShader.glsl";
    private static final String FRAGMENT_FILE = "shaders/guiFragmentShader.glsl";

    private Map<String, List<Integer>> uniformLocations;

    public GuiShader() throws IOException {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "positionCoords");
    }

    @Override
    protected void initUniformLocations() {
        if (uniformLocations == null) {
            uniformLocations = new HashMap<>();
        }
        uniformLocations.put("transformationMatrix", List.of(getUniformLocation("transformationMatrix")));
    }

    @Override
    public Map<String, List<Integer>> getUniformLocations() {
        return uniformLocations;
    }
}
