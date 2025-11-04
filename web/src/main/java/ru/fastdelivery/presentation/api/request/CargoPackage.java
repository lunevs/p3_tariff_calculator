package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(

        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Длинна упаковки, миллиметры", example = "350")
        BigInteger length,

        @Schema(description = "Ширина упаковки, миллиметры", example = "150")
        BigInteger width,

        @Schema(description = "Высота упаковки, миллиметры", example = "250")
        BigInteger height
) {
}
