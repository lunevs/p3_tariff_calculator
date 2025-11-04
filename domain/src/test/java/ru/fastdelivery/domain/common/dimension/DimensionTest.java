package ru.fastdelivery.domain.common.dimension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DimensionTest {

    @Test
    @DisplayName("Попытка создать отрицательный объем -> исключение")
    void whenMetresBelowZero_thenException() {
        var negativeDimension = new BigInteger("-1");
        var normalDimension = new BigInteger("100");
        assertThatThrownBy(() -> Dimension.of(negativeDimension, normalDimension, normalDimension))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Dimension.of(normalDimension, negativeDimension, normalDimension))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Dimension.of(normalDimension, normalDimension, negativeDimension))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({ "100, 100",
            "100, 100",
            "200, 200",
            "123, 150" ,
            "201, 250",
            "489, 500" })
    void roundDimensions_thenOk(BigInteger input, BigInteger expected) {
        var dimension = Dimension.of(input, input, input);

        assertThat(dimension.getWidth()).isEqualTo(expected);
        assertThat(dimension.getLength()).isEqualTo(expected);
        assertThat(dimension.getHeight()).isEqualTo(expected);
    }

    @Test
    void equalsTypeVolume_same() {
        var dim1 = new BigInteger("100");
        var dim2 = new BigInteger("200");
        var dim3 = new BigInteger("300");

        var dimension1 = Dimension.of(dim1, dim2, dim3);
        var dimension2 = Dimension.of(dim1, dim3, dim2);

        assertThat(dimension1)
                .isEqualTo(dimension2);
    }

    @Test
    void equalsNull_false() {
        var dim1 = new BigInteger("100");
        var dim2 = new BigInteger("200");
        var dim3 = new BigInteger("300");

        var dimension1 = Dimension.of(dim1, dim2, dim3);

        assertThat(dimension1).isNotEqualTo(null);
    }

    @ParameterizedTest
    @CsvSource({ "100, 100, 100, 1000000",
            "100, 100, 200, 2000000",
            "200, 200, 200, 8000000" })
    void calculateVolumeTest(BigInteger s1, BigInteger s2, BigInteger s3, BigInteger expected) {
        var dimension = Dimension.of(s1, s2, s3);

        assertThat(dimension.getVolume())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Первый размер больше второго -> true")
    void whenFirstDimensionGreaterThanSecond_thenTrue() {
        var dim1 = new BigInteger("100");
        var dim2 = new BigInteger("200");
        var dim3 = new BigInteger("300");

        var dimensionSmall = Dimension.of(dim1, dim2, dim3);
        var dimensionBig = Dimension.of(dim1, dim2, dim3.multiply(BigInteger.TWO));

        assertThat(dimensionBig.greaterThan(dimensionSmall)).isTrue();
    }

    @Test
    @DisplayName("Перевод объема в метру кубические -> получено корректное значение")
    void whenGetMetres_thenOk() {
        var dim1 = new BigInteger("100");
        var dim2 = new BigInteger("200");

        var dimension1 = Dimension.of(dim1, dim1, dim1);
        var dimension2 = Dimension.of(dim2, dim2, dim2);

        assertThat(dimension1.getVolumeInMetres()).isEqualByComparingTo(new BigDecimal("0.001"));
        assertThat(dimension2.getVolumeInMetres()).isEqualByComparingTo(new BigDecimal("0.008"));
    }
}