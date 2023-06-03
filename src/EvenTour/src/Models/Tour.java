package Models;

import java.util.*;

public class Tour {
    // TourBase
    private int id;
    private String route;
    private Date date;
    private int distance;
    private int price;

    public Tour() {

    }
    public Tour(int id, String route, Date date, int distance, int price) {
        this.id = id;
        this.route = route;
        this.date = date;
        this.distance = distance;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
