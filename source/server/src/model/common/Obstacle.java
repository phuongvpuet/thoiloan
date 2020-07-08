package model.common;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class Obstacle {
    public int obstacleId;
    public ObstacleType obstacleType;
    public int posX;
    public int posY;
    public long startTime;

    public Obstacle(int obstacleId, ObstacleType obstacleType, int posX, int posY) {
        this.obstacleId = obstacleId;
        this.obstacleType = obstacleType;
        this.posX = posX;
        this.posY = posY;
        this.startTime = 0;
    }

    public int getObstacleId() {
        return obstacleId;
    }

    public void setObstacleId(int obstacleId) {
        this.obstacleId = obstacleId;
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public void setObstacleType(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getRemainingTime() {
        if (this.startTime == 0) {
            return 0;
        } else {
            long remainingTime = 0;
            // TODO: check need time to clean this obstacle in config
            return remainingTime;
        }
    }
}
