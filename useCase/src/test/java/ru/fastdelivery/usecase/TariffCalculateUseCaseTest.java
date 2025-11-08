package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Dimension;
import ru.fastdelivery.domain.common.distance.CoordinatesCheckProvider;
import ru.fastdelivery.domain.common.distance.Distance;
import ru.fastdelivery.domain.common.distance.DistanceFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final DistanceCalculateProvider distanceCalculateProvider = mock(DistanceCalculateProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, distanceCalculateProvider);

    @Test
    @DisplayName("Расчет стоимости доставки по весу -> успешно")
    void whenCalculatePriceWhenWeightIsBigger_thenSuccess() {
        var dimension = Dimension.of(BigInteger.valueOf(100), BigInteger.valueOf(100), BigInteger.valueOf(100));
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerMeter = new Price(BigDecimal.valueOf(100), currency);

        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(volumePriceProvider.costPerMeter()).thenReturn(pricePerMeter);
        when(distanceCalculateProvider.calcDistanceCoefficient(any())).thenReturn(BigDecimal.ONE);

        var shipment = new Shipment(
                List.of(
                        new Pack(new Weight(BigInteger.valueOf(1200)), dimension)
                ),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(120), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, BigDecimal.valueOf(450));

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Расчет стоимости доставки по объему -> успешно")
    void whenCalculatePriceWhenVolumeIsBigger_thenSuccess() {
        var dimension = Dimension.of(BigInteger.valueOf(1000), BigInteger.valueOf(1000), BigInteger.valueOf(1000));
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerMeter = new Price(BigDecimal.valueOf(1000), currency);

        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(volumePriceProvider.costPerMeter()).thenReturn(pricePerMeter);
        when(distanceCalculateProvider.calcDistanceCoefficient(any())).thenReturn(BigDecimal.ONE);

        var shipment = new Shipment(
                List.of(
                        new Pack(new Weight(BigInteger.valueOf(1200)), dimension)
                ),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(1000), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, BigDecimal.valueOf(450));

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Расчет стоимости доставки по объему и тройное расстояние -> успешно")
    void whenCalculatePriceWhenVolumeAndTripleDistance_thenSuccess() {
        int distanceCoefficient = 3;
        var dimension = Dimension.of(BigInteger.valueOf(1000), BigInteger.valueOf(1000), BigInteger.valueOf(1000));
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerMeter = new Price(BigDecimal.valueOf(1000), currency);

        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(volumePriceProvider.costPerMeter()).thenReturn(pricePerMeter);
        when(distanceCalculateProvider.calcDistanceCoefficient(any())).thenReturn(BigDecimal.valueOf(distanceCoefficient));

        var shipment = new Shipment(
                List.of(
                        new Pack(new Weight(BigInteger.valueOf(1200)), dimension)
                ),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(1000 * distanceCoefficient), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, BigDecimal.valueOf(450 * distanceCoefficient));

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPriceForWeight();

        assertThat(actual).isEqualTo(minimalPrice);
    }
}