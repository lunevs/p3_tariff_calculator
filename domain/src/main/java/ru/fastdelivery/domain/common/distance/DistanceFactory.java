package ru.fastdelivery.domain.common.distance;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class DistanceFactory {

    private final CoordinatesCheckProvider checker;

    public Distance create(BigDecimal departureLatitude, BigDecimal departureLongitude, BigDecimal destinationLatitude, BigDecimal destinationLongitude) {
        if (departureLatitude == null || departureLongitude == null || destinationLatitude == null || destinationLongitude == null) {
            throw new IllegalArgumentException("latitude and longitude can't be empty");
        }
        if (!checker.checkLatitude(departureLatitude) || !checker.checkLatitude(destinationLatitude)) {
            throw new IllegalArgumentException("latitude has incorrect value: " + departureLatitude + ", " + destinationLatitude);
        }
        if (!checker.checkLongitude(departureLongitude) || !checker.checkLongitude(destinationLongitude)) {
            throw new IllegalArgumentException("longitude has incorrect value: " + departureLongitude + ", " + destinationLongitude);
        }
        return new Distance(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude);
    }
}
