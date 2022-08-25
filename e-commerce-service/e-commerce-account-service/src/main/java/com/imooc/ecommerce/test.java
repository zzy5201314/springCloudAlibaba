package com.imooc.ecommerce;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/16
 */
public class test {

    public static void main(String[] args) {
        pow(3, 5);
    }

    public static Long pow(int x, int y) {
        return new Double(Math.pow(x, y)).longValue();
    }

    public static Long calcSumOfMedian(int[] x, int[] y) {

        Long result = 0L;
        List<Integer> a = Arrays.stream(x).boxed().collect(Collectors.toList());
        List<Integer> b = Arrays.stream(y).boxed().collect(Collectors.toList());
        boolean is = a.addAll(b);
        if (is) {
            List<Integer> c = a.stream().sorted().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(c)) {
                int median = 0;
                // 小数以及整数
                BigDecimal number = new BigDecimal(c.size()).divide(new BigDecimal(2))
                        .setScale(2,BigDecimal.ROUND_HALF_UP);
                // 判断是否整除
                if (new BigDecimal(number.intValue()).compareTo(number) == 0){
                    median = number.intValue();
                    result =  a.get(median).longValue();
                }else {
                    median = number.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();

                    BigDecimal result1 = new BigDecimal(a.get(median - 1)).add(new BigDecimal(a.get(median)));
                    result = result1.divide(new BigDecimal(2)).setScale(0,BigDecimal.ROUND_HALF_UP).longValue();
                }
            }
        }

        return result;
    }
}
