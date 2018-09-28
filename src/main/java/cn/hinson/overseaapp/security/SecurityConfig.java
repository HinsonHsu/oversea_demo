package cn.hinson.overseaapp.security;

import cn.hinson.overseaapp.enums.OauthType;
import cn.hinson.overseaapp.security.oauth2.ClientResources;
import cn.hinson.overseaapp.security.oauth2.OauthPrincipalExtractor;
import cn.hinson.overseaapp.security.service.CustomerUserDetailService;
import cn.hinson.overseaapp.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

/**
 * Created by xinshengshu on 2018/9/27.
 */
@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
//        auth.inMemoryAuthentication()
//        .passwordEncoder(new BCryptPasswordEncoder())
//        .withUser("user1")
//        .password(new BCryptPasswordEncoder().encode("123456"))
//        .roles("USER");
        // @formatter:on
        auth.userDetailsService(getUserDetailService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //authorizeRequests方法限定只对签名成功的用户请求
        //anyRequest方法限定所有请求
        //authenticated方法对所有签名成功的用户允许方法
        // @formatter:off
//        http.authorizeRequests()
//            .antMatchers("/", "/login**", "/test1", "/login/github").permitAll()
//            .anyRequest().authenticated()
//            .and().exceptionHandling()
//            .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
//            .formLogin();
//        httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //临时取消csrf，为了让postman访问
        httpSecurity.csrf().disable();
        httpSecurity
            .antMatcher("/**")  //1
            .authorizeRequests()
            .antMatchers("/", "/login**", "/webjars/**", "/error**", "/test", "/register").permitAll()  //2
            .anyRequest().authenticated()  //3
            .and().exceptionHandling()
            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))  //4
            .and()
            .logout().logoutSuccessUrl("/").permitAll()
            .and()
            .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class); //5
        // @formatter:on
    }


    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
        OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("github")
    public ClientResources github() {
        return new ClientResources(OauthType.GITHUB);
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources(OauthType.FACEBOOK);
    }

    @Bean
    public UserDetailsService getUserDetailService() {
        return new CustomerUserDetailService();
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(facebook(), "/login/facebook"));
        filters.add(ssoFilter(github(), "/login/github"));
        filter.setFilters(filters);
        return filter;
    }

    @Autowired
    UserService userService;

    private Filter ssoFilter(ClientResources client, String path) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
////        filter.setAuthenticationSuccessHandler(successHandler);
//        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient());
//        filter.setRestTemplate(template);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
////        tokenServices.setPrincipalExtractor(new OauthPrincipalExtractor(client.getOauth2Type()));
//        tokenServices.setRestTemplate(template);
//        filter.setTokenServices(tokenServices);
//        return filter;

        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);
        filter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
            client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(template);
        tokenServices.setPrincipalExtractor(new OauthPrincipalExtractor(client.getOauth2Type(), userService));
        filter.setTokenServices(tokenServices);
        return filter;
    }


}
