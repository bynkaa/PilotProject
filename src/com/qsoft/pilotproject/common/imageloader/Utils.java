package com.qsoft.pilotproject.common.imageloader;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: BinkaA
 * Date: 11/6/13
 * Time: 10:29 PM
 */
public class Utils {
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}