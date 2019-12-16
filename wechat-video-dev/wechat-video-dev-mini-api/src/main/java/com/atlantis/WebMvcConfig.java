package com.atlantis;

import com.atlantis.controller.interceptor.MiniInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/6
 * @Description: Spring内部的一种配置方式, 采用JavaBean的形式来代替传统的xml配置文件
 * @version: 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    // 用户资源路径
    @Value("${FileSpace}")
    public String FileSpace;

    // 将http请求路径路径映射成本地资源路径
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // SwaggerAPI接口文档映射
                .addResourceLocations("classpath:/META-INF/resources/")
                // 用户资源路径映射
                .addResourceLocations("file:" + FileSpace + "/");
    }

    // 注册拦截器
    @Bean
    public MiniInterceptor miniInterceptor() {
        return new MiniInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
                .addPathPatterns("/video/upload", "/video/uploadCover",
                        "/video/userLike", "/video/userUnLike",
                        "/video/saveComment")
                .excludePathPatterns("/user/queryPublisher","/bgm/**");

        super.addInterceptors(registry);
    }
}