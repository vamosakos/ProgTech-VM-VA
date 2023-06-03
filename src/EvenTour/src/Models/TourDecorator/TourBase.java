package Models.TourDecorator;

import java.util.Date;

public abstract class TourBase {
    protected String route;
    protected Date date;
    protected int distance;
    protected int price;
    public TourBase( String route, Date date, int distance, int price) {
        this.route = route;
        this.date = date;
        this.distance = distance;
        this.price = price;
    }

    public abstract String getRoute();

    public void setRoute(String route) {
        this.route = route;
    }

    public abstract Date getDate();

    public void setDate(Date date) {
        this.date = date;
    }

    public abstract int getDistance();


    public void setDistance(int distance) {
        this.distance = distance;
    }

    public abstract int getPrice();

    public void setPrice(int price) {
        this.price = price;
    }
}
