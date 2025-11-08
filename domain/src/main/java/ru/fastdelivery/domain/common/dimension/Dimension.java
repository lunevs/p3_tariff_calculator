package ru.fastdelivery.domain.common.dimension;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.MessageFormat;

@Getter
public class Dimension implements Comparable<Dimension>  {

    private final BigInteger height;
    private final BigInteger width;
    private final BigInteger length;

    private Dimension(BigInteger height, BigInteger width, BigInteger length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }

    public static Dimension of(BigInteger height, BigInteger width, BigInteger length) {
        if (height == null || width == null || length == null) {
            throw new IllegalArgumentException("Dimensions can't be empty!");
        }
        if (isLessThanZero(height) || isLessThanZero(width) || isLessThanZero(length)) {
            throw new IllegalArgumentException("Dimensions cannot be below Zero!");
        }
        return new Dimension(roundToFifty(height), roundToFifty(width), roundToFifty(length));
    }

    public BigDecimal getVolumeInMetres() {
        BigDecimal result = new BigDecimal(getVolume());
        return result
                .divide(BigDecimal.valueOf(1_000_000_000), 4, RoundingMode.HALF_UP);
    }

    public BigInteger getVolume() {
        return height.multiply(width).multiply(length);
    }

    @Override
    public int compareTo(Dimension o) {
        return getVolume().compareTo(o.getVolume());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        return getVolume().compareTo(dimension.getVolume()) == 0;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Height x Width x Length = {0} x {1} x {2}", height, width, length);
    }

    private static BigInteger roundToFifty(BigInteger value) {
        BigInteger fifty = BigInteger.valueOf(50);
        BigInteger[] divideAndRemainder = value.divideAndRemainder(fifty);
        BigInteger quotient = divideAndRemainder[0];
        BigInteger remainder = divideAndRemainder[1];
        if (remainder.compareTo(BigInteger.ZERO) > 0) {
            return quotient.add(BigInteger.ONE).multiply(fifty);
        }
        return value;
    }

    private static boolean isLessThanZero(BigInteger dimension) {
        return BigInteger.ZERO.compareTo(dimension) > 0;
    }

    public boolean greaterThan(Dimension d) {
        return getVolume().compareTo(d.getVolume()) > 0;
    }

}
