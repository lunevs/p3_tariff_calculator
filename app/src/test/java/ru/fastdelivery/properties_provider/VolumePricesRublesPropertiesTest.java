package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.properties.provider.VolumePricesRublesProperties;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VolumePricesRublesPropertiesTest {

    public static final BigDecimal PER_METER = BigDecimal.valueOf(50);
    public static final BigDecimal MINIMAL = BigDecimal.valueOf(100);
    public static final String RUB = "RUB";
    final CurrencyFactory currencyFactory = mock(CurrencyFactory.class);
    VolumePricesRublesProperties properties;

    @BeforeEach
    void init(){
        properties = new VolumePricesRublesProperties(currencyFactory);

        properties.setRubPerMetre(PER_METER);
        properties.setMinimalPricePerMetre(MINIMAL);

        var currency = mock(Currency.class);
        when(currency.getCode()).thenReturn(RUB);

        when(currencyFactory.create(RUB)).thenReturn(currency);
    }

    @Test
    void whenCallPricePerMetre_thenRequestFromConfig() {
        var actual = properties.costPerMeter();

        verify(currencyFactory).create("RUB");
        assertThat(actual.amount()).isEqualByComparingTo(PER_METER);
        assertThat(actual.currency().getCode()).isEqualTo("RUB");
    }

    @Test
    void whenCallMinimalPrice_thenRequestFromConfig() {
        var actual = properties.minimalPrice();

        verify(currencyFactory).create("RUB");
        assertThat(actual.amount()).isEqualByComparingTo(MINIMAL);
        assertThat(actual.currency().getCode()).isEqualTo("RUB");
    }
}