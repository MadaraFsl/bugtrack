package com.bugtracking.config;

import com.bugtracking.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

//    // 暂时取消加密机制
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder(){
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**",
                "/images/**", "/css/**", "/js/**", "/plugins/**", "/static/**", "/fonts/**");
    }

    // security5.0之后需要密码加密
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        // 指定加密方法为BCrypt
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()  //定义哪些url需要保护，哪些url不需要保护
                .antMatchers("/").permitAll()    //定义不需要认证就可以访问
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/login").successForwardUrl("/login")    //定义当需要用户登录时候，转到的登录页面
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.headers().frameOptions().disable();
        http.csrf().disable();
    }
}
