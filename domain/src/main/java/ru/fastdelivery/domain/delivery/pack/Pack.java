package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimension.Dimension;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;
import java.text.MessageFormat;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight, Dimension dimension) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final BigInteger maxHeightMillimetres = BigInteger.valueOf(1500);
    private static final BigInteger maxWidthMillimetres = BigInteger.valueOf(1500);
    private static final BigInteger maxLengthMillimetres = BigInteger.valueOf(1500);

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
        if (dimensionsGreaterThanMax(dimension)) {
            String maxSizeFormat = MessageFormat.format("{0} x {1} x {2}", maxHeightMillimetres, maxWidthMillimetres, maxLengthMillimetres);
            throw new IllegalArgumentException("Dimensions cannot be greater than " + maxSizeFormat);
        }

    }

    private static boolean dimensionsGreaterThanMax(Dimension dimension) {
        return dimension.getLength().compareTo(maxLengthMillimetres) > 0 ||
                dimension.getHeight().compareTo(maxHeightMillimetres) > 0 ||
                dimension.getWidth().compareTo(maxWidthMillimetres) > 0;
    }

}
