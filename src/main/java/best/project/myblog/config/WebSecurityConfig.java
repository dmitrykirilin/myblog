package best.project.myblog.config;

import best.project.myblog.models.Permission;
import best.project.myblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
//                        .antMatchers(HttpMethod.GET, "api/**").hasAuthority(Permission.USER_READ.getPermission())
//                        .antMatchers(HttpMethod.POST, "api/**").hasAuthority(Permission.USER_WRITE.getPermission())
//                        .antMatchers(HttpMethod.PUT, "api/**").hasAuthority(Permission.USER_WRITE.getPermission())
//                        .antMatchers(HttpMethod.DELETE, "api/**").hasAuthority(Permission.USER_WRITE.getPermission())
                .anyRequest().authenticated()
                .and()
//                    .httpBasic();
//                .and()
                    .formLogin()
                    .loginPage("/auth/login").permitAll()
                    .defaultSuccessUrl("/auth/success")
                    .and()
//                    .failureUrl("/login?error=true")
//                    .permitAll()
//                .and()
//                    .rememberMe()
//                .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/login")
//                .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/auth/login");
//                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }
}
