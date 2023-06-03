package model.tourDecorator;

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

    public abstract Date getDate();

    public abstract int getDistance();

    public abstract int getPrice();

}
