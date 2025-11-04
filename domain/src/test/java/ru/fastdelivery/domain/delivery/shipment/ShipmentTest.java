package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Dimension;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightAndVolumeOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var defaultSize1 = BigInteger.valueOf(100);
        var dimension1 = Dimension.of(defaultSize1, defaultSize1, defaultSize1);
        var defaultSize2 = BigInteger.valueOf(200);
        var dimension2 = Dimension.of(defaultSize2, defaultSize2, defaultSize2);

        var packages = List.of(new Pack(weight1, dimension1), new Pack(weight2, dimension2));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var massOfShipment = shipment.weightAllPackages();
        var volumeOfShipment = shipment.volumeAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
        assertThat(volumeOfShipment).isEqualByComparingTo(BigDecimal.valueOf(0.009));
    }
}