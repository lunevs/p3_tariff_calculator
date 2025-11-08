package ru.fastdelivery.domain.common.distance;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class DistanceFactory {

    private final CoordinatesCheckProvider checker;

    public Distance create(BigDecimal destinationLongitude, BigDecimal destinationLatitude, BigDecimal departureLongitude, BigDecimal departureLatitude) {
        if (checker.checkLatitude(departureLatitude) || checker.checkLatitude(destinationLatitude)) {
            throw new IllegalArgumentException("latitude has incorrect value");
        }
        if (checker.checkLongitude(departureLongitude) || checker.checkLongitude(destinationLongitude)) {
            throw new IllegalArgumentException("longitude has incorrect value");
        }
        return new Distance(destinationLongitude, destinationLatitude, departureLongitude, departureLatitude);
    }
}
