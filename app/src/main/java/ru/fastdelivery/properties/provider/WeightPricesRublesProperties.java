package ru.fastdelivery.properties.provider;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.WeightPriceProvider;

import java.math.BigDecimal;

/**
 * Настройки базовых цен стоимости перевозки из конфига
 */
@Component
@Setter
@RequiredArgsConstructor
public class WeightPricesRublesProperties implements WeightPriceProvider {

    @Value("${cost.rub.perKg}")
    private BigDecimal perKg;

    @Value("${cost.rub.minimalPricePerKg}")
    private BigDecimal minimalPricePerKg;

    private final CurrencyFactory currencyFactory;

    @Override
    public Price costPerKg() {
        return new Price(perKg, currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPrice() {
        return new Price(minimalPricePerKg, currencyFactory.create("RUB"));
    }
}
