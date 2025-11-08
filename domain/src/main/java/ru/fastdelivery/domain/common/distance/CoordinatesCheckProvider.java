package ru.fastdelivery.domain.common.distance;

import java.math.BigDecimal;

public interface CoordinatesCheckProvider {

    boolean checkLatitude(BigDecimal latitude);
    boolean checkLongitude(BigDecimal longitude);
}
