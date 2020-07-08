package util.database;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public abstract class UserDataModel extends DataModel {
    public int userId;

    public UserDataModel(int userId) {
        this.userId = userId;
    }

    public void save(){
        try {
            saveModel(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
