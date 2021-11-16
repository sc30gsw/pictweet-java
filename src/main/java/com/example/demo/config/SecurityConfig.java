package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**セキュリティ適用外*/
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/webjars/**")
				.antMatchers("/css/**")
				.antMatchers("/js/**");
	}
	
	/**直リンクの設定*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//ログイン不要ページの設定
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated();
		
		//ログイン処理
		http
			.formLogin()
				.loginProcessingUrl("/login")//ログイン処理のパス
				.loginPage("/login")//ログインページの指定
				.failureUrl("/login?error")//ログイン失敗時の遷移先の指定
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true);//ログイン成功時の遷移先の指定
		
	}
	
	/**認証の設定*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();
		
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder);
	}

}
