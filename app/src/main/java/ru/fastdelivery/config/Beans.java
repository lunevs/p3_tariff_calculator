package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.common.distance.CoordinatesCheckProvider;
import ru.fastdelivery.domain.common.distance.DistanceFactory;
import ru.fastdelivery.properties.provider.CoordinatesCheckProperties;
import ru.fastdelivery.usecase.DistanceCalculateService;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider, VolumePriceProvider volumePriceProvider) {
        return new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider);
    }

    @Bean
    public DistanceCalculateService distanceCalculateService() {
        return new DistanceCalculateService();
    }

    @Bean
    public CoordinatesCheckProvider checker() {
        return new CoordinatesCheckProperties();
    }

    @Bean
    public DistanceFactory distanceFactory() {
        return new DistanceFactory(checker());
    }
}
