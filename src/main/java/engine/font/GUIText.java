package engine.font;

import com.google.common.flogger.FluentLogger;
import engine.font.structure.FontType;
import engine.model.Model;
import engine.model.TextModel;
import game.object.RenderObject;
import engine.shader.Shader;
import game.state.Game;
import game.ui.ObjectType;
import game.ui.UIComponent;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GUIText extends RenderObject {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private String text;
    private float fontSize;
    private TextModel model;
    private Vector2f position;
    private float maxLineLength;
    private int lineCount;
    private FontType font;
    private boolean centerText;

    public static class Builder {

        private final String text;

        private FontType font = Game.getInstance().getGameFont();
        private Vector3f colour = new Vector3f(1);
        private float fontSize = 1;
        private Vector2f position = new Vector2f();
        private float maxLineLength = 1;
        private boolean centerText = false;

        public Builder(String text) {
            this.text = text;
        }

        public Builder fontSize(float fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder position(Vector2f position) {
            this.position = position;
            return this;
        }

        public Builder lineLength(float maxLineLength) {
            this.maxLineLength = maxLineLength;
            return this;
        }

        public Builder centered(boolean centerText) {
            this.centerText = centerText;
            return this;
        }

        public Builder colour(Vector3f colour) {
            this.colour = colour;
            return this;
        }

        public GUIText build() {
            logger.atInfo().log("Created a GUI text for text - %s", text);
            return new GUIText(this);
        }
    }

    private GUIText(Builder builder) {
        this.text = builder.text;
        this.fontSize = builder.fontSize;
        this.model = new TextModel(builder.colour);
        this.position = builder.position;
        this.maxLineLength = builder.maxLineLength;
        this.font = builder.font;
        this.centerText = builder.centerText;
    }

    public String getText() {
        return text;
    }

    public float getFontSize() {
        return fontSize;
    }

    public int getID() {
        return font.getTextureAtlas();
    }

    public void setMeshInfo(int vaoID, int vertexCount) {
        model.setModelID(vaoID);
        model.setVertexCount(vertexCount);
    }

    public void setColour(float r, float g, float b) {
        model.setColour(new Vector3f(r, g, b));
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Vector3f getPosition() {
        return null;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    @Override
    public Vector3f getRotation() {
        return new Vector3f();
    }

    @Override
    public Vector3f getScaleVector() {
        return new Vector3f(1);
    }

    @Override
    public void prepareObject(Shader shader) {
        shader.doLoad2DVector(position, "translation");
    }

    public float getMaxLineLength() {
        return maxLineLength;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public FontType getFont() {
        return font;
    }

    public boolean isCentered() {
        return centerText;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ObjectType getType() {
        return ObjectType.TEXT;
    }

    public GUIText copyWithValueChange(String newValue) {
        GUIText copy = this;
        copy.setText(newValue);
        return copy;
    }
}
