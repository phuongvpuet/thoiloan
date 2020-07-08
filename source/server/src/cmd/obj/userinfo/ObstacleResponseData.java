package cmd.obj.userinfo;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class ObstacleResponseData {
    public int obstacleId;
    public String obstacleType;
    public int posX;
    public int posY;
    public long timeRemaining;

    public int getObstacleId() {
        return obstacleId;
    }

    public void setObstacleId(int obstacleId) {
        this.obstacleId = obstacleId;
    }

    public String getObstacleType() {
        return obstacleType;
    }

    public void setObstacleType(String obstacleType) {
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

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
