package com.imooc.ecommerce.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.ecommerce.Enum.MaskEnum;
import jdk.nashorn.internal.runtime.FindProperty;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import sun.reflect.Reflection;

import java.lang.reflect.Field;
import java.sql.Struct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * TODO: 数据加密规则转换
 *
 * @author zzy
 * @date 2022/9/22
 */
public class DataMaskingUtils {

    /**
     * TODO: 加密 姓名
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskName(String name) {
        return "客户姓名";
    }

    /**
     * TODO: 加密 身份证
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskIdCard(String idCard) {
        final Integer id_Card_Length = 18;
        if (!StringUtils.isEmpty(idCard) && StrUtil.length(idCard) == id_Card_Length) {
            return StrUtil.subPre(idCard, 6) + "****" + StrUtil.subSufByLength(idCard, 4);
        }
        return idCard;
    }

    /**
     * TODO: 加密 手机号码
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskMobilePhone(String mobilePhone) {
        final Integer mobile_Phone_Length = 11;
        if (!StringUtils.isEmpty(mobilePhone) && StrUtil.length(mobilePhone) == mobile_Phone_Length) {
            return StrUtil.subPre(mobilePhone, 3) + "****" + StrUtil.subSufByLength(mobilePhone, 4);
        }
        return mobilePhone;
    }

    /**
     * TODO: 加密 邮件
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskEmail(String email) {
        if (!StringUtils.isEmpty(email) && StrUtil.contains(email, "@")) {
            return StrUtil.subPre(email, 3) + "****" +
                    StrUtil.subSuf(email, StrUtil.indexOf(email, '@'));
        }
        return email;
    }

    /**
     * TODO: 加密 银行卡
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskBankCard(String bankCard) {
        final Integer bank_Card_Length = 10;
        if (!StringUtils.isEmpty(bankCard) && StrUtil.length(bankCard) > bank_Card_Length) {
            return StrUtil.subPre(bankCard, 6) + "****" +
                    getLengthStar(StrUtil.length(bankCard) - 10) +
                    StrUtil.subSufByLength(bankCard, 4);
        }
        return bankCard;
    }

    public static String getLengthStar(int starLength) {
        StringBuilder star = new StringBuilder();
        for (int i = 0; i < starLength; i++) {
            star.append("*");
        }
        return star.toString();
    }

    /**
     * TODO: 加密 个人地址
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskAddress(String address) {
        return "个人地址";
    }

    /**
     * TODO: 加密 工作地址
     *
     * @Author : zzy
     * @Date 2022/9/22 15:13
     * @param: name
     * @return: java.lang.String
     */
    public static String MaskWorkAddress(String workAddress) {
        return "工作地址";
    }

    public static final Map<List<String>, Function<String, String>> maskDataInit(Map<String, List<String>> map) {
        Map<List<String>, Function<String, String>> resultMap = new HashMap<>();
        resultMap.put(map.get(MaskEnum.PERSON_NAME.getFiled()), (name) -> MaskName(name));
        resultMap.put(map.get(MaskEnum.PERSON_ID_CARD.getFiled()), (idCard) -> MaskName(idCard));
        resultMap.put(map.get(MaskEnum.PERSON_MOBILE_PHONE.getFiled()), (mobilePhone) -> MaskName(mobilePhone));
        resultMap.put(map.get(MaskEnum.PERSON_EMAIL.getFiled()), (email) -> MaskName(email));
        resultMap.put(map.get(MaskEnum.PERSON_BANKCARD.getFiled()), (bankCard) -> MaskName(bankCard));
        resultMap.put(map.get(MaskEnum.PERSON_ADDRESS.getFiled()), (address) -> MaskName(address));
        resultMap.put(map.get(MaskEnum.PERSON_WORK_ADDRESS.getFiled()), (workAddress) -> MaskName(workAddress));
        return resultMap;
    }

    public static <T> void MaskList(Map<String, List<String>> map, List<T> list) {
        if (CollectionUtils.isEmpty(map) || CollectionUtils.isEmpty(list)) {
            return;
        }

        list.stream().forEach(data -> {
            MaskObject(map, data);
        });
    }

    public static Object MaskObject(Map<String, List<String>> map, Object obj) {
        if (CollectionUtils.isEmpty(map) || ObjectUtils.isEmpty(obj)) {
            return obj;
        }

        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            //  代码安全检查出setAccessible(true)会存在安全问题
            // 使用spring提供工具类解决 ReflectionUtils.makeAccessible(field)
            ReflectionUtils.makeAccessible(field);
            try {
                Object tar = field.get(obj);
                if (tar instanceof JSONObject) {
                    JSONObject _json = (JSONObject) tar;
                    MaskJSONObject(map, _json);
                    field.set(obj, _json);
                }
                if (tar instanceof String) {
                    Function<String, String> maskF = getMaskFunByKey(map, field.getName());
                    if (ObjectUtils.isEmpty(maskF)) {
                        field.set(obj, maskF.apply((String) tar));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return obj;
    }

    public static void MaskJSONObject(Map<String, List<String>> map, JSONObject json) {
        if (CollectionUtils.isEmpty(map) || ObjectUtils.isEmpty(json)) {
            return;
        }
        json.entrySet().stream().forEach(kv -> {
            if (kv.getValue() instanceof String) {
                Function<String, String> maskF = getMaskFunByKey(map, kv.getKey());
                if (ObjectUtils.isEmpty(maskF)) {
                    json.put(kv.getKey(), maskF.apply((String) kv.getValue()));
                }
            }
            if (kv.getValue() instanceof JSONArray) {
                JSONArray listData = (JSONArray) kv.getValue();
                for (int i = 0; i < listData.size(); i++) {
                    JSONObject _json;
                    try {
                        _json = listData.getJSONObject(i);
                    } catch (Exception ex) {
                        continue;
                    }
                    MaskObject(map, _json);
                }
            }
            if (kv.getValue() instanceof JSONObject) {
                MaskJSONObject(map, (JSONObject) kv.getValue());
            }
        });
    }

    public static Function<String, String> getMaskFunByKey(Map<String, List<String>> map, String key) {
        if (CollectionUtils.isEmpty(maskDataInit(map).keySet())) {
            return null;
        }
        for (List<String> containList : maskDataInit(map).keySet()) {
            if (!CollectionUtils.isEmpty(containList) && containList.contains(key)) {
                return maskDataInit(map).get(containList);
            }
        }
        return null;
    }

}
