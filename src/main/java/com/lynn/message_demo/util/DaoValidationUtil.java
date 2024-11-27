package com.lynn.message_demo.util;

import java.util.Arrays;
import java.util.function.IntSupplier;

/**
 * @Author: Lynn on 2024/11/26
 */
public class DaoValidationUtil {
  public static void validateResultIsOne(IntSupplier action, Object... paramArgs) {
    int count = action.getAsInt();
    if (count != 1) {
      String classNameString = Arrays.toString(Arrays.stream(paramArgs).map(obj -> obj.getClass().getSimpleName()).toArray());
      throw new RuntimeException(String.format("RESULT MUST BE ONE,count:%s, class:%s, param:%s", count, classNameString, Arrays.toString(paramArgs)));
    }
  }
}
