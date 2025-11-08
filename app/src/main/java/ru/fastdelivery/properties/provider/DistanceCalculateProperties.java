package ru.fastdelivery.properties.provider;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.usecase.DistanceCalculateProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Настройки валют из конфига
 */
@Configuration
@Getter
public class DistanceCalculateProperties implements DistanceCalculateProvider {

    @Value("${coordinates.distance.min}")
    private int distanceMin;


    @Override
    public BigDecimal calcDistanceCoefficient(BigDecimal distance) {
        if (distance.compareTo(BigDecimal.valueOf(distanceMin)) < 0) {
            return BigDecimal.ONE;
        } else {
            return distance.divide(BigDecimal.valueOf(distanceMin), 2, RoundingMode.UP);
        }
    }
}
