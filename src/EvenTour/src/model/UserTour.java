package model;

public class UserTour {
    private int id;
    private int userId;
    private int tourId;

    public UserTour() {}

    //region properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    //endregion
}
