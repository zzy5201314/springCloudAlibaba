package com.imooc.ecommerce.filter;

import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.util.TokenParseUtil;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO: 用户身份统一登录拦截
 * <p>
 * 1.日志，2.注册到容器中为一个Bean，3.屏蔽所有警告
 *
 * @author zzy
 * @date 2022/8/11
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 部分请求不需要带有身份信息，即白名单
        if (checkWhiltListUrl(request.getRequestURI())){
            return true;
        }

        // 先尝试从 HTTP header 里面拿到 token
        String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);

        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {
            log.error("parse login user info error: [{}]", ex.getMessage(), ex);
        }

        // 如果程序走到这里，说明 heard 中没有 token 信息
        if (ObjectUtils.isEmpty(loginUserInfo)){
            throw new RuntimeException("can not parse current login user");
        }

        log.info("set login user info: [{}]",request.getRequestURI());
        // 设置当前请求上下文，把用户信息填充进去
        AccessContext.setLoginUserInfo(loginUserInfo);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在请求完全结束后调用，常用于清理资源等工作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (!ObjectUtils.isEmpty(AccessContext.getLoginUserInfo())){
            AccessContext.clearLoginUserInfo();
        }
    }

    /**
     * 校验是否是白名单接口
     * swagger 接口
     * @param url
     * @return
     */
    private boolean checkWhiltListUrl(String url){

        return StringUtils.containsAny(
                url,
                "springfox","swagger","v2","webjars","doc.html"
        );
    }
}
