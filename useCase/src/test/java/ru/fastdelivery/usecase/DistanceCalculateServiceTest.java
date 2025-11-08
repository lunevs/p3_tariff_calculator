package ru.fastdelivery.usecase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;


public class DistanceCalculateServiceTest {

    final DistanceCalculateService service = new DistanceCalculateService();

    private static final int EARTH_RADIUS = 6372795;

    @ParameterizedTest
    @CsvSource({ "77.1539, -139.398, -77.1804, -139.55, 17166029",
            "77.1539, 120.398, 77.1804, 129.55, 225883",
            "77.1539, -120.398, 77.1804, 129.55, 2332669",
            "45, 30, 51, 46, 1360948"})
    void calculateDistanceTest(double x1, double y1, double x2, double y2, BigDecimal distance) {

        BigDecimal result = service.calculateDistanceInRadians(x1, y1, x2, y2)
                .multiply(BigDecimal.valueOf(EARTH_RADIUS));

        assertThat(result.setScale(0, RoundingMode.HALF_UP))
                .isEqualTo(distance);
    }



}
