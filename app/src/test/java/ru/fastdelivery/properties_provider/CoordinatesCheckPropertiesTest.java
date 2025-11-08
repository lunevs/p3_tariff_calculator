package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.fastdelivery.properties.provider.CoordinatesCheckProperties;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoordinatesCheckPropertiesTest {

    private CoordinatesCheckProperties properties = new CoordinatesCheckProperties();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(properties, "latitudeMin", 45);
        ReflectionTestUtils.setField(properties, "latitudeMax", 65);
        ReflectionTestUtils.setField(properties, "longitudeMin", 30);
        ReflectionTestUtils.setField(properties, "longitudeMax", 96);
    }


    @Test
    @DisplayName("Проверка на неверные значения")
    public void whenIncorrectValues_thenFalse() {
        assertThat(properties.checkLatitude(BigDecimal.TEN)).isFalse();
        assertThat(properties.checkLatitude(BigDecimal.valueOf(100000))).isFalse();

        assertThat(properties.checkLongitude(BigDecimal.TEN)).isFalse();
        assertThat(properties.checkLongitude(BigDecimal.valueOf(100000))).isFalse();

    }

    @Test
    @DisplayName("Проверка на правильные значения")
    public void whenCorrectValues_thenTrue() {
        assertThat(properties.checkLatitude(BigDecimal.valueOf(45))).isTrue();
        assertThat(properties.checkLatitude(BigDecimal.valueOf(55))).isTrue();
        assertThat(properties.checkLatitude(BigDecimal.valueOf(65))).isTrue();

        assertThat(properties.checkLongitude(BigDecimal.valueOf(30))).isTrue();
        assertThat(properties.checkLongitude(BigDecimal.valueOf(85))).isTrue();
        assertThat(properties.checkLongitude(BigDecimal.valueOf(96))).isTrue();
    }
}
