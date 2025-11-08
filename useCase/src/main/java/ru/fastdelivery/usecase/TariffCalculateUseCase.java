package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final DistanceCalculateProvider distanceCalculateProvider;

    public Price calc(Shipment shipment, BigDecimal distance) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackages = shipment.volumeAllPackages();
        var minimalPriceByWeight = weightPriceProvider.minimalPrice();
        var minimalPriceByVolume = volumePriceProvider.minimalPrice();

        Price volumePrice = volumePriceProvider
                .costPerMeter()
                .multiply(volumeAllPackages)
                .max(minimalPriceByVolume);

        Price resultPrice = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPriceByWeight)
                .max(volumePrice);

        return resultPrice.multiply(distanceCalculateProvider.calcDistanceCoefficient(distance));
    }

    public Price minimalPriceForWeight() {
        return weightPriceProvider.minimalPrice();
    }

    public Price minimalPriceForVolume() {
        return volumePriceProvider.minimalPrice();
    }

}
