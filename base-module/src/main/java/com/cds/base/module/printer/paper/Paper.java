/**
 * @Project base-module
 * @Package com.cds.base.module.printer.paper
 * @Class Paper.java
 * @Date Jan 6, 2020 3:06:34 PM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer.paper;

import java.io.Serializable;

/**
 * @Description 纸张规格
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 6, 2020 3:06:34 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer width;
    private Integer height;
    private Units units;

    /**
     * Create a new instance Paper.
     *
     * @param width
     * @param height
     * @param units
     */
    public Paper(Integer width, Integer height, Units units) {
        super();
        this.width = width;
        this.height = height;
        this.units = units;
    }

    /**
     * serialversionuid
     *
     * @return serialversionuid
     */

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * width
     *
     * @return width
     */

    public Integer getWidth() {
        return width;
    }

    /**
     * height
     *
     * @return height
     */

    public Integer getHeight() {
        return height;
    }

    /**
     * units
     *
     * @return units
     */

    public Units getUnits() {
        return units;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @param units
     *            the units to set
     */
    public void setUnits(Units units) {
        this.units = units;
    }

}
