package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.fastdelivery.properties.provider.DistanceCalculateProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DistanceCalculatePropertiesTest {

    private static final int MIN_DISTANCE = 450_000;

    private DistanceCalculateProperties properties = new DistanceCalculateProperties();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(properties, "distanceMin", MIN_DISTANCE);
    }

    @Test
    public void whenCalcDistanceCoefficientLessMin_thenOne() {

        assertThat(properties.calcDistanceCoefficient(BigDecimal.ONE)
                .compareTo(BigDecimal.ONE)).isZero();
        assertThat(properties.calcDistanceCoefficient(BigDecimal.valueOf(MIN_DISTANCE / 2))
                .compareTo(BigDecimal.ONE)).isZero();
        assertThat(properties.calcDistanceCoefficient(BigDecimal.valueOf(MIN_DISTANCE))
                .compareTo(BigDecimal.ONE)).isZero();
    }

    @Test
    public void whenCalcDistanceCoefficient_thenOk() {

        double k1 = 1.677;
        double k2 = 2.234234;
        double k3 = 434.647453453;
        double k4 = 10;

        assertThat(properties.calcDistanceCoefficient(BigDecimal.valueOf(MIN_DISTANCE * k1))
                        .compareTo(BigDecimal.valueOf(k1).setScale(2, RoundingMode.UP))).isZero();
        assertThat(properties.calcDistanceCoefficient(BigDecimal.valueOf(MIN_DISTANCE * k2))
                        .compareTo(BigDecimal.valueOf(k2).setScale(2, RoundingMode.UP))).isZero();
        assertThat(properties.calcDistanceCoefficient(BigDecimal.valueOf(MIN_DISTANCE * k3))
                        .compareTo(BigDecimal.valueOf(k3).setScale(2, RoundingMode.UP))).isZero();
        assertThat(properties.calcDistanceCoefficient(BigDecimal.valueOf(MIN_DISTANCE * k4))
                        .compareTo(BigDecimal.valueOf(k4).setScale(2, RoundingMode.UP))).isZero();

    }
}
