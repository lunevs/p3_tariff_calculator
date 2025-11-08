package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final DistanceCalculateProvider distanceCalculateProvider;

    Logger log = Logger.getLogger(this.getClass().getName());

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

        BigDecimal distanceCoefficient = distanceCalculateProvider.calcDistanceCoefficient(distance);
        log.info("Calculated distance coefficient: " + distanceCoefficient.doubleValue());
        return resultPrice.multiply(distanceCoefficient);
    }

    public Price minimalPriceForWeight() {
        return weightPriceProvider.minimalPrice();
    }

    public Price minimalPriceForVolume() {
        return volumePriceProvider.minimalPrice();
    }

}
