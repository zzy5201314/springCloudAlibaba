package com.imooc.ecommerce.utils;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * TODO: 列表操作工具类
 *
 * @author zzy
 * @date 2022/8/30
 */
public class ListCustomUtils {

    /**
     * TODO: 对 List 进行分页
     *
     * @Author : zzy
     * @Date 2022/8/30 11:28
     * @param: list
     * @param: pageNo
     * @param: pageSize
     * @return: java.util.List<T>
     */
    public static <T> List<T> toPageList(List<T> list, int pageNo, int pageSize) {
        final int batchSize = pageSize;
        long totalPage = list.size() / batchSize;
        if (list.size() % batchSize > 0) {
            pageSize++;
        }

        int fromIndex = (pageNo - 1) * batchSize;
        int toIndex = pageNo * batchSize;
        if (pageNo == totalPage) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }

    /**
     * TODO: 在内存中对list排序
     *
     * @Author : zzy
     * @Date 2022/8/30 11:42
     * @param: list
     * @param: orderBy
     * @param: orderType
     * @return: java.util.List<T>
     */
    public static <T> List<T> listSort(List<T> list, String orderBy, String orderType) {
        if (StringUtils.isEmpty(orderBy) || StringUtils.isEmpty(orderType)) {
            return list;
        }
        // 排序
        list.sort(new Comparator<T>() {
            @SneakyThrows
            @Override
            public int compare(T o1, T o2) {
                Object obj1 = findObject(o1,orderBy);
                if (obj1 == null || StringCustomUtils.isNumeric(obj1.toString()) == false) {
                    return -1;
                }
                Double value1 = Double.valueOf(obj1.toString());

                Object obj2 = findObject(o2,orderBy);
                if (obj2 == null || StringCustomUtils.isNumeric(obj2.toString()) == false) {
                    return -1;
                }
                Double value2 = Double.valueOf(obj2.toString());

                if ("asc".equals(orderType)) {
                    return value1.compareTo(value2);
                }
                return value2.compareTo(value1);
            }
        });
        return list;
    }

    /**
     * TODO: 通过反射获取对象属性值
     *
     * @Author : zzy
     * @Date 2022/8/30 11:43
     * @param: o
     * @param: orderBy
     * @return: java.lang.Object
     */
    public static <T> Object findObject(T o, String orderBy) throws Exception {
        if(o instanceof Map){
            // 兼容Map类型
            return ((Map) o).get(orderBy);
        }else{
            Field field = o.getClass().getDeclaredField(orderBy);
            field.setAccessible(true);
            return field.get(o);
        }
    }
}
