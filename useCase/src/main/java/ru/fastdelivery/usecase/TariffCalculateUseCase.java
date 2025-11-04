package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackages = shipment.volumeAllPackages();
        var minimalPriceByWeight = weightPriceProvider.minimalPrice();
        var minimalPriceByVolume = volumePriceProvider.minimalPrice();

        Price volumePrice = volumePriceProvider
                .costPerMeter()
                .multiply(volumeAllPackages)
                .max(minimalPriceByVolume);

        return weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPriceByWeight)
                .max(volumePrice);
    }

    public Price minimalPriceForWeight() {
        return weightPriceProvider.minimalPrice();
    }

    public Price minimalPriceForVolume() {
        return volumePriceProvider.minimalPrice();
    }

}
