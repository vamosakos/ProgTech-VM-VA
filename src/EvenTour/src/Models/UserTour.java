package Models;

public class UserTour {
    private int id;
    private int userId;
    private int tourId;

    public UserTour() {}
    public UserTour(int id, int userId, int tourId) {
        this.id = id;
        this.userId = userId;
        this.tourId = tourId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
}
