/**
 * @author LYU
 * @create 2017-11-10-9:04
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.convert;

import java.io.IOException;

public interface BaseDataOutput {
    <T> String writeObject(T t) throws IOException;
}
