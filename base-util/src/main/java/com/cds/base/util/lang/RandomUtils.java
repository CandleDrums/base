/**
 * @Project base.util
 * @Package com.cds.base.lang.util
 * @Class RandomUtils.java
 * @Date 2016年8月18日 上午11:09:23
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.util.lang;

import java.util.Random;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月18日 上午11:09:23
 * @version 1.0
 * @since JDK 1.8
 */
public class RandomUtils {

    /**
     * Random object used by random method. This has to be not local to the random method so as to not return the same
     * value in the same millisecond.
     */
    private static final Random RANDOM = new Random();

    /**
     * <p>
     * {@code RandomUtils} instances should NOT be constructed in standard programming. Instead, the class should be
     * used as {@code RandomUtils.nextBytes(5);}.
     * </p>
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.
     * </p>
     */
    public RandomUtils(){
        super();
    }

    /**
     * <p>
     * Creates an array of random bytes.
     * </p>
     * 
     * @param count the size of the returned array
     * @return the random byte array
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public static byte[] nextBytes(final int count) {
        Validate.isTrue(count >= 0, "Count cannot be negative.");

        final byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
    }

    /**
     * <p>
     * Returns a random integer within the specified range.
     * </p>
     * 
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive the upper bound (not included)
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if {@code startInclusive} is
     * negative
     * @return the random integer
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
        Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    /**
     * <p>
     * Returns a random long within the specified range.
     * </p>
     * 
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive the upper bound (not included)
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if {@code startInclusive} is
     * negative
     * @return the random long
     */
    public static long nextLong(final long startInclusive, final long endExclusive) {
        Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return (long) nextDouble(startInclusive, endExclusive);
    }

    /**
     * <p>
     * Returns a random double within the specified range.
     * </p>
     * 
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive the upper bound (included)
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if {@code startInclusive} is
     * negative
     * @return the random double
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        Validate.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }

    /**
     * <p>
     * Returns a random float within the specified range.
     * </p>
     * 
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive the upper bound (included)
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if {@code startInclusive} is
     * negative
     * @return the random float
     */
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        Validate.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextFloat());
    }
}
