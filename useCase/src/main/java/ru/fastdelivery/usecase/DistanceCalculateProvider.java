package ru.fastdelivery.usecase;

import java.math.BigDecimal;

public interface DistanceCalculateProvider {

    BigDecimal calcDistanceCoefficient(BigDecimal distance);

}
