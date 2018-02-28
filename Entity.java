import java.awt.*;

public class Entity {

    private static long nextEntityId = 1;

    private long id;
    private double x;
    private double y;
    private double orientation; // 0 Represents north/up, going clockwise to 359.9999
    private int movementSpeed = 10;

    private Color color;
    private int size;

    private double targetX;
    private double targetY;

    private EntityAction entityAction = EntityAction.MOVING;

    public Entity() {
        this.size = 30;
        this.color = Color.WHITE;
        this.id = nextEntityId;
        nextEntityId++;
    }

    public void update() {
        if (entityAction == EntityAction.MOVING) {
            if (getDistanceToTargetPoint() < movementSpeed) { // We are near the end. Move to the point and then stop moving
                x = targetX;
                y = targetY;
                entityAction = EntityAction.NONE;
            } else { // We aren't near the end of our movement target. Move closer to it
                double deltaY = Math.cos(Math.toRadians(orientation)) * movementSpeed;
                double deltaX = Math.sin(Math.toRadians(orientation)) * movementSpeed;

                x += deltaX;
                y -= deltaY; // Subtraction here because Swing has a backwards Y orientation
            }
        }
    }

    public void moveToPoint(double x, double y) {
        targetX = x;
        targetY = y;
        entityAction = EntityAction.MOVING;
        updateOrientation();
    }

    private void updateOrientation() {
        double deltaX = x - targetX;
        double deltaY = y - targetY;

        double radianAngle = Math.atan2(deltaY, deltaX);
        double degreeAngle = Math.toDegrees(radianAngle) + 180;

        orientation = (degreeAngle + 90) % 360; // This last step puts '0' as 'up'
    }

    private double getDistanceToTargetPoint() {
        return Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public EntityAction getEntityAction() {
        return entityAction;
    }

    public void setEntityAction(EntityAction entityAction) {
        this.entityAction = entityAction;
    }
}
