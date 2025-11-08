package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.distance.Distance;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class DistanceCalculateService {

    private static final int EARTH_RADIUS = 6372795;

    public BigDecimal calculateDistance(Distance distance) {
        return BigDecimal.valueOf(EARTH_RADIUS)
                .multiply(calculateDistanceInRadians(
                        distance.getDepartureLatitude().doubleValue(),
                        distance.getDepartureLongitude().doubleValue(),
                        distance.getDestinationLatitude().doubleValue(),
                        distance.getDestinationLongitude().doubleValue()
                ))
                .setScale(0, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateDistanceInRadians(double departureLatitude, double departureLongitude, double destinationLatitude, double destinationLongitude) {

        double l1 = departureLatitude * Math.PI / 180;
        double l2 = destinationLatitude * Math.PI / 180;
        double long1 = departureLongitude * Math.PI / 180;
        double long2 = destinationLongitude * Math.PI / 180;

        double cl1 = Math.cos(l1);
        double cl2 = Math.cos(l2);
        double sl1 = Math.sin(l1);
        double sl2 = Math.sin(l2);
        double deltaL = long2 - long1;
        double cdelta = Math.cos(deltaL);
        double sdelta = Math.sin(deltaL);

        double numerator = numerator(cl1, cl2, sl1, sl2, cdelta, sdelta);
        double denominator = denominator(cl1, cl2, sl1, sl2, cdelta);

        if (denominator == 0) {
            throw new IllegalArgumentException("Cannot calculate distance because denominator is zero");
        }

        double atan = Math.atan(Math.abs(numerator/denominator));
        double dist = (denominator > 0) ? atan : Math.PI - atan;

        return BigDecimal.valueOf(dist);
    }

    private double numerator(double cl1, double cl2, double sl1, double sl2, double cdelta, double sdelta) {
        double p1 = Math.pow(cl2 * sdelta, 2);
        double p2 = Math.pow((cl1*sl2) - (sl1*cl2 * cdelta), 2);
        return Math.pow(p1 + p2, 0.5);
    }

    private double denominator(double cl1, double cl2, double sl1, double sl2, double cdelta) {
        double p4 = sl1*sl2;
        double p5 = cl1*cl2 * cdelta;
        return p4 + p5;
    }

}
