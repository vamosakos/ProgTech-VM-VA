package model;

import java.util.*;

public class Tour {
    private int id;
    private String route;
    private Date date;
    private int distance;
    private int price;

    public Tour() {

    }

    //region properties

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setDate(Date date) { this.date = date; }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //endregion
}
