package ru.fastdelivery.domain.common.distance;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Distance {
    BigDecimal destinationLongitude;
    BigDecimal destinationLatitude;
    BigDecimal departureLongitude;
    BigDecimal departureLatitude;
}
