package com.atlantis.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/17
 * @Description: com.atlantis.interceptor
 * @version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    private List<String> unCheckUrls; // 需要放行的url集合

    public List<String> getUnCheckUrls() {
        return unCheckUrls;
    }

    public void setUnCheckUrls(List<String> unCheckUrls) {
        this.unCheckUrls = unCheckUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String requestUrl = request.getRequestURI();
        requestUrl = requestUrl.replaceAll(request.getContextPath(), "");
        // 判断是否针对匿名路径需要拦截，如果包含，则表示匿名路径，需要拦截，否则通过拦截器
        if (unCheckUrls.contains(requestUrl)) {
            // 包含需要放行的url，直接跳过
            return true;
        }
        if (null == request.getSession().getAttribute("sessionAdmin")) {
            response.sendRedirect(request.getContextPath() + "/login.action");
            return false;
        }
        // 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
