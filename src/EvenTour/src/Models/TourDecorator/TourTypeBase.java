package Models.TourDecorator;

import java.util.Date;

public class TourTypeBase extends TourBase {

    public TourTypeBase(TourBase tourBase) {
        super(tourBase.route, tourBase.date, tourBase.distance, tourBase.price);
        this.tourBase = tourBase;
    }

    private TourBase tourBase;

    @Override
    public String getRoute() {
        return tourBase.getRoute();
    }

    @Override
    public Date getDate() {
        return tourBase.getDate();
    }

    @Override
    public int getDistance() {
        return tourBase.getDistance();
    }

    @Override
    public int getPrice() {
        return tourBase.getPrice();
    }
}
