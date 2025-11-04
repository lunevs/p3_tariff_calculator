package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.Dimension;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var dimension = Dimension.of(BigInteger.valueOf(100), BigInteger.valueOf(200), BigInteger.valueOf(300));
        assertThatThrownBy(() -> new Pack(weight, dimension))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenDimensionMoreThanMax_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(100_000));
        var dimension = Dimension.of(BigInteger.valueOf(10000), BigInteger.valueOf(200), BigInteger.valueOf(300));
        assertThatThrownBy(() -> new Pack(weight, dimension))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWeightAndDimensionLessThanMax_thenObjectCreated() {
        var dimension = Dimension.of(BigInteger.valueOf(100), BigInteger.valueOf(200), BigInteger.valueOf(300));
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)), dimension);
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }

}