package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.distance.CoordinatesCheckProvider;

import java.math.BigDecimal;

@Configuration
@Setter
public class CoordinatesCheckProperties implements CoordinatesCheckProvider {

    @Value("${coordinates.latitude.max}")
    private int latitudeMax;

    @Value("${coordinates.latitude.min}")
    private int latitudeMin;

    @Value("${coordinates.longitude.max}")
    private int longitudeMax;

    @Value("${coordinates.longitude.min}")
    private int longitudeMin;


    @Override
    public boolean checkLatitude(BigDecimal latitude) {
        return latitude.compareTo(BigDecimal.valueOf(latitudeMax)) >= 0 &&
                latitude.compareTo(BigDecimal.valueOf(latitudeMin)) <= 0;
    }

    @Override
    public boolean checkLongitude(BigDecimal longitude) {
        return longitude.compareTo(BigDecimal.valueOf(longitudeMax)) >= 0 &&
                longitude.compareTo(BigDecimal.valueOf(longitudeMin)) <= 0;
    }
}
