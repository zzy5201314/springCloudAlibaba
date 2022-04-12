package com.imooc.ecommerce.constant;

/**
 * <h1>通用模块常量定义</h1>
 *
 * @author zzy
 * @date 2022/4/13
 */
public final class CommonConstant {

    /**
     * RSA 公钥
     */
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi60tntRqacKcGsUHtJBSrBX4TTPfK9i" +
            "egQZ7WxaSPFxP8raT62h384YmcNwssGoM9y6SSplePdfAZ2esB74GpuDYWB/g3oy3+00r0b5ulgpEHRIzS5cb/8RuWGClHhbi+1MGnKbfcD" +
            "dQEAW8KYkq19Wsoke8oa85hhjWLm7olEFUTjB8Z0zccO4cXGEsYXSoG7h/+pvh5YZy1oavtv5xi650Np12DYQtUkuP6vxNkGfXBDCTozWMk" +
            "FxI3amgrfzmYVLhFYgn5wUOq8ACBdSoi3wZPjKerRbo1XpBehlpi5lKIXYQm4kQlwcyVo1xjc//XUMlbEpW1h9vP7SlG2FOuQIDAQAB";

    /** JWT 中存储用户信息的 key */
    public static final String JWT_USER_INFO_KEY = "e-commerce-user";

    /** 授权中心的 service-id 即授权中心的 ${spring.application.name} */
    public static final String AUTHORITY_CENTER_SERVICE_ID = "e-commerce-authority-center";


}
