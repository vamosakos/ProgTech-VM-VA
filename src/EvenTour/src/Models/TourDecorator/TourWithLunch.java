package Models.TourDecorator;

import java.util.Date;

public class TourWithLunch extends TourBase {
    public TourWithLunch(String route, Date date, int distance, int price) {
        super(route, date, distance, price + 500);
    }


    @Override
    public String getRoute() {
        return this.route + " (WL)";
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public int getPrice() {
        return this.price;
    }
}
