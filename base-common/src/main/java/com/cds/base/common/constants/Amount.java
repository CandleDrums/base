/**
 * @Project base-common
 * @Package com.cds.base.common.constants
 * @Class Amount.java
 * @Date 2019年09月04日 上午10:59:51
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.common.constants;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 金额
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月16日 上午10:59:51
 * @version 1.0
 * @since JDK 1.8
 */
public class Amount implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5243845895706371162L;

    /**
     * 金额值，默认单位为元
     */
    private BigDecimal value;

    /**
     * 精度
     */
    private int accuracy;

    /**
     * 默认精度
     */
    private static final int DEFAULT_ACCURACY = 2;
    /**
     * 分进位
     */
    private final static long CENT_CARRY = 100L;

    /**
     * 分进位
     */
    private final static BigDecimal CENT_CARRY_DEIMAL = new BigDecimal(CENT_CARRY);
    /**
     * 允许的最小值
     */
    private static final BigDecimal MIN_VALUE = new BigDecimal(-1000000000000L);

    /**
     * 允许的最大值
     */
    private static final BigDecimal MAX_VALUE = new BigDecimal(1000000000000L);

    /**
     * 无参构造器
     */
    public Amount() {
        super();
    }

    /**
     * 不带精度的构造器，默认两位精度<br/>
     * <strong style="color:red">单位:元<strong/>
     * 
     * @param value
     */
    public Amount(BigDecimal value) {
        this(value, DEFAULT_ACCURACY);
    }

    /**
     * 不带精度的构造器，默认两位精度<br/>
     * <strong style="color:red">单位:元<strong/>
     * 
     * @param value
     */
    public Amount(String value) {
        this(new BigDecimal(value), DEFAULT_ACCURACY);
    }

    /**
     * 分进制，默认精度2 Create a new instance Amount.
     *
     * @param value
     */
    public Amount(Long value) {
        this(new BigDecimal(value).divide(CENT_CARRY_DEIMAL), DEFAULT_ACCURACY);
    }

    /**
     * 分进制，默认精度2 Create a new instance Amount.
     *
     * @param value
     */
    public Amount(double value) {
        this(new BigDecimal(value).multiply(CENT_CARRY_DEIMAL), DEFAULT_ACCURACY);
    }

    /**
     * 带精度的构造器
     * 
     * @param value
     * @param accuracy
     */
    public Amount(BigDecimal value, int accuracy) {
        // 检查金额数值范围是否合法
        if (value == null || value.compareTo(MIN_VALUE) == -1 || value.compareTo(MAX_VALUE) == 1) {
            throw new RuntimeException("无效的金额数值[value=" + value + "]");
        }
        // 将金额数值从第N+1位四舍五入，保留N位小数
        this.value = value.setScale(accuracy, BigDecimal.ROUND_HALF_UP);
        this.accuracy = accuracy;
    }

    public final Amount abs() {
        return new Amount(value.abs());
    }

    /**
     * 判断本金额是否大于某金额
     * 
     * @param amount
     * @return
     */
    public final boolean isGreaterThan(Amount amount) {
        return value.compareTo(amount.getValue()) > 0;
    }

    /**
     * 判断本金额是否大于某金额
     * 
     * @param amount
     * @return
     */
    public final boolean isGreaterThan(String amount) {
        return value.compareTo(new BigDecimal(amount)) > 0;
    }

    /**
     * 判断本金额是否等于某金额
     * 
     * @param amount
     * @return
     */
    public final boolean isEqualTo(Amount amount) {
        return value.compareTo(amount.getValue()) == 0;
    }

    /**
     * 判断本金额是否小于某金额
     * 
     * @param amount
     * @return
     */
    public final boolean isLesserThan(Amount amount) {
        return value.compareTo(amount.getValue()) < 0;
    }

    /**
     * 判断本金额是否为正数
     * 
     * @return
     */
    public final boolean isPositive() {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断本金额是否为0
     * 
     * @return
     */
    public final boolean isZero() {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断本金额是否为负数
     * 
     * @return
     */
    public final boolean isNegative() {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 金额相加 public final Amount add(Amount amount) { return new Amount(this.value.add(amount.getValue())); }
     * 
     * @param amount
     *            被加金额
     * @return
     */

    public final void add(Amount amount) {
        this.value = this.value.add(amount.getValue());
    }

    /**
     * 金额相加，返回值
     * 
     * @param amount
     *            被加金额
     * @return
     */
    public final Amount addBack(Amount amount) {
        return new Amount(this.value.add(amount.getValue()));
    }

    /**
     * 金额相加 <br/>
     * <strong style="color:red">replace method : void add(Amount amount)...<strong/>
     * 
     * @param amount
     */
    @Deprecated
    public final void add(String amount) {
        BigDecimal val = new BigDecimal(amount);
        this.value = this.value.add(val);
    }

    /**
     * 金额相加<br/>
     * <strong style="color:red">replace method : void add(Amount amount)...<strong/>
     * 
     * @param augend
     */
    @Deprecated
    public final void add(BigDecimal augend) {
        if (augend != null) {
            this.value = this.value.add(augend);
        }
    }

    /**
     * 金额相减<br/>
     * <strong style="color:red">数据相减,有返回值<strong/>
     * 
     * @param amount
     *            被减金额
     * @return
     */
    public final Amount subtract(Amount amount) {
        return new Amount(this.value.subtract(amount.getValue()));
    }

    /**
     * 金额相减<br/>
     * <strong style="color:red">数据相减,无返回值<strong/>
     * 
     * @param amount
     *            被减金额
     * @return
     */
    public final void subtractNOBack(Amount amount) {
        if (amount != null) {
            this.value = this.value.subtract(amount.getValue());
        }
    }

    /**
     * 金额相乘
     * 
     * @param multiplicand
     */
    public final void multiply(BigDecimal multiplicand) {
        if (multiplicand != null) {
            this.value = this.value.multiply(multiplicand);
        }
    }

    /**
     * 相乘
     * 
     * @param d
     * @return
     */
    public final Amount multiply(String d) {
        BigDecimal bigValue2 = new BigDecimal(d);
        return new Amount(value.multiply(bigValue2));
    }

    /**
     * 相除
     * 
     * @param d
     * @return
     */
    public final Amount divide(String d) {
        BigDecimal bigValue = new BigDecimal(d);
        bigValue = value.divide(bigValue, DEFAULT_ACCURACY, BigDecimal.ROUND_HALF_UP);
        return new Amount(bigValue);
    }

    /**
     * 相除
     * 
     * @param d
     * @return
     */
    public final BigDecimal divide(Amount d) {
        return value.divide(d.getValue(), DEFAULT_ACCURACY, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取金额值<br/>
     * <strong style="color:red">单位:元<strong/>
     * 
     * @return
     */
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * 获取金额值<br/>
     * <strong style="color:red">单位:元<strong/>
     * 
     * @return
     */
    public String getYuanValue() {
        if (this.value == null) {
            return null;
        }
        return value.setScale(accuracy, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 获取分值<br/>
     * <strong style="color:red">单位:分<strong/>
     * 
     * @return
     */
    public Long getCentValue() {
        if (this.value == null) {
            return null;
        }
        return value.setScale(accuracy, BigDecimal.ROUND_HALF_UP).multiply(CENT_CARRY_DEIMAL).longValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (!(obj instanceof Amount)) {
            throw new IllegalArgumentException("无效参数");
        }
        Amount other = (Amount)obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

}
