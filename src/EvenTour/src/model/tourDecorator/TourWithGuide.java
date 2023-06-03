package model.tourDecorator;

public class TourWithGuide extends TourTypeBase{

    public TourWithGuide (TourBase tourBase) {
        super(tourBase);
    }

    
    @Override
    public String getRoute() {
        return super.getRoute() + " (WG)";
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 3000;
    }

}
