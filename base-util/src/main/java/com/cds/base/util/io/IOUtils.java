/**
 * @Project base-util
 * @Package com.cds.base.util.io
 * @Class IOUtils.java
 * @Date 2018年3月2日 下午3:54:38
 * @Copyright (c) CandleDrumS.com All Right Reserved.
 */
package com.cds.base.util.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author ming.li
 * @Date 2018年3月2日 下午3:54:38
 * @version 1.0
 * @since JDK 1.8
 */
public class IOUtils {

    private IOUtils(){
        throw new AssertionError();
    }

    /**
     * Close closable object and wrap {@link IOException} with {@link RuntimeException}
     * 
     * @param closeable closeable object
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            }
        }
    }

    /**
     * Close closable and hide possible {@link IOException}
     * 
     * @param closeable closeable object
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignored
            }
        }
    }

}
