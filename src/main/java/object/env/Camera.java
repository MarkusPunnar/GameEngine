package object.env;

import com.google.common.flogger.FluentLogger;
import engine.DisplayManager;
import game.state.GameState;
import game.state.State;
import interraction.MousePicker;
import object.Player;
import object.RenderObject;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import util.CameraUtil;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private static final float MAX_PITCH = 70;
    private static final float MIN_PITCH = -70;
    private static final float PITCH_SPEED = 35;
    private static final float YAW_SPEED = 100;

    private static final float DISTANCE_FROM_PLAYER = 17;

    private Player player;
    private MousePicker mousePicker;
    private Vector3f position;
    private float pitch;
    private Vector3f movementCache;
    private CameraState state;

    public Camera(Player player) {
        this.player = player;
        this.position = new Vector3f();
        pitch = 10;
        if (glfwRawMouseMotionSupported()) {
            glfwSetInputMode(DisplayManager.getWindow(), GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
            logger.atInfo().log("Enabled raw mouse motion");
        }
        setStateNormal();
    }

    public void checkState() {
        if (GameState.getInstance().getCurrentState().equals(State.IN_GAME)) {
            glfwSetInputMode(DisplayManager.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        } else {
            glfwSetInputMode(DisplayManager.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }

    public void move(List<RenderObject> renderObjects) {
        Vector2f mouseCoords = mousePicker.calculateDeviceCoords();
        pitch += mouseCoords.y * PITCH_SPEED;
        pitch = Math.max(Math.min(pitch, MAX_PITCH), MIN_PITCH);
        float horisontalRotation = -mouseCoords.x * YAW_SPEED;
        player.getRotation().y += (Math.abs(horisontalRotation)) < 10 ? horisontalRotation : Math.signum(horisontalRotation) * 8;
        GLFW.glfwSetCursorPos(DisplayManager.getWindow(), DisplayManager.getWidth() / 2f, DisplayManager.getHeight() / 2f);
        CameraUtil.calculateCameraPosition(this, renderObjects);
    }

    public Player getPlayer() {
        return player;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setMousePicker(MousePicker mousePicker) {
        this.mousePicker = mousePicker;
    }

    public Matrix4f createProjectionMatrix() {
        return new Matrix4f().perspective(((float) Math.toRadians(CameraUtil.FOV)),
                DisplayManager.getAspectRatio(), CameraUtil.NEAR_PLANE, CameraUtil.FAR_PLANE);
    }

    public float getDistanceFromPlayer() {
        return DISTANCE_FROM_PLAYER;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getMovementCache() {
        return movementCache;
    }

    public boolean hasMovementCached() {
        return movementCache != null;
    }

    public void setMovementCache(Vector3f movementCache) {
        this.movementCache = movementCache;
    }

    public CameraState getState() {
        return state;
    }

    public void setStateZoom() {
        this.state = CameraState.ZOOMED;
    }

    public void setStateNormal() {
        this.state = CameraState.NORMAL;
    }

    public boolean isZoomed() {
        return state == CameraState.ZOOMED;
    }

    private enum CameraState {
        ZOOMED,
        NORMAL;
    }
}
