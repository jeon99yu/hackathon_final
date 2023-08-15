package co_2.suggest_project.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder())
            .withUser("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")  // ADMIN 역할을 가진 사용자만 접근 가능
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/login") // 로그인 페이지 URL
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and()
            .csrf().disable(); // CSRF 비활성화 (나중에 활성화하거나 보다 더 정교한 설정을 추가할 수 있음)
    }
}
