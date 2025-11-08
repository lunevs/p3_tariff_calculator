package ru.fastdelivery.usecase;

import java.math.BigDecimal;

public interface DistanceCalculateProvider {

    public BigDecimal calcDistanceCoefficient(BigDecimal distance);

}
