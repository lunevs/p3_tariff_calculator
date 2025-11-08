package ru.fastdelivery.domain.common.distance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DistanceFactoryTest {

    CoordinatesCheckProvider checker = mock(CoordinatesCheckProvider.class);
    DistanceFactory distanceFactory = new DistanceFactory(checker);

    @Test
    @DisplayName("Не верные значения  -> исключение")
    public void whenIncorrectInput_thenThrow() {

        BigDecimal bd = BigDecimal.valueOf(55);

        when(checker.checkLatitude(any())).thenReturn(false);
        when(checker.checkLongitude(any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> distanceFactory.create(bd, bd, bd, bd));

    }

    @Test
    @DisplayName("Корректные значения -> правильный инстанс")
    public void whenCorrectInput_thenReturnDistance() {

        BigDecimal bd = BigDecimal.valueOf(55);

        when(checker.checkLatitude(any())).thenReturn(true);
        when(checker.checkLongitude(any())).thenReturn(true);

        assertThat(distanceFactory.create(bd, bd, bd, bd))
                .isInstanceOf(Distance.class);

    }

    @Test
    @DisplayName("Корректные значения -> правильно заполнены атрибуты")
    public void whenCorrectInput_thenCorrectCoordinates() {

        BigDecimal bd1 = new BigDecimal("11");
        BigDecimal bd2 = new BigDecimal("12");
        BigDecimal bd3 = new BigDecimal("13");
        BigDecimal bd4 = new BigDecimal("14");

        when(checker.checkLatitude(any())).thenReturn(true);
        when(checker.checkLongitude(any())).thenReturn(true);

        Distance distance = distanceFactory.create(bd1, bd2, bd3, bd4);

        assertThat(distance.getDepartureLatitude()).isEqualTo(bd1);
        assertThat(distance.getDepartureLongitude()).isEqualTo(bd2);
        assertThat(distance.getDestinationLatitude()).isEqualTo(bd3);
        assertThat(distance.getDestinationLongitude()).isEqualTo(bd4);
    }
}
