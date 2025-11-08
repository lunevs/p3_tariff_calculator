package ru.fastdelivery.properties.provider;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.VolumePriceProvider;

import java.math.BigDecimal;

@Configuration
@Setter
@RequiredArgsConstructor
public class VolumePricesRublesProperties implements VolumePriceProvider {

    @Value("${cost.rub.perMetre}")
    private BigDecimal rubPerMetre;

    @Value("${cost.rub.minimalPricePerMetre}")
    private BigDecimal minimalPricePerMetre;

    private final CurrencyFactory currencyFactory;

    @Override
    public Price costPerMeter() {
        return new Price(rubPerMetre, currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPrice() {
        return new Price(minimalPricePerMetre, currencyFactory.create("RUB"));
    }
}
