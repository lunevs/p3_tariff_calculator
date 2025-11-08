package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MapPoint(

        @Schema(description = "Широта заданной точки на карте", example = "73.398660")
        @NotNull
        BigDecimal latitude,

        @Schema(description = "Долгота заданной точки на карте", example = "55.027532")
        @NotNull
        BigDecimal longitude
) {
}
