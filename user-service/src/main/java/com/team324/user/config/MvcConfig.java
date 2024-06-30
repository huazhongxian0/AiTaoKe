package com.team324.user.config;


import com.team324.user.common.LoginUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置拦截器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {                                         //拦截器顺序
        registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**").order(0);
       /* registry.addInterceptor(new LoginInterceptor())
                //排除不需要拦截的路径
                .excludePathPatterns(
                        "/shop/**",
                        "/voucher/**",
                        " /shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login"
                ).order(1);*/

    }
}