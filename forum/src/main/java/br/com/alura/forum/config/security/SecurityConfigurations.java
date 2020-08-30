package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
    // @Autowired
    // private H2ConsoleProperties console;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManager();
    }
    
	// Autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	// Autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {       
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable() // cross-site request forgery -> nao é necessário qdo tem token
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
		
		/// **************************************************
		/// ATENCAO -- > NAO HABILITAR ESTE CONSOLE EM PRODUCAO		
		/*String path = this.console.getPath();
        String antPattern = (path.endsWith("/") ? path + "**" : path + "/**");
        HttpSecurity h2Console = http.antMatcher(antPattern);
        h2Console.csrf().disable();
        h2Console.httpBasic();
        h2Console.headers().frameOptions().sameOrigin(); */
        /// **************************************************
	}
	
	// Recursos estaticos (css, js, imagens, etc. )
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	
	/*
	public static void main(String[] args) {
		System.out.println( new BCryptPasswordEncoder().encode("123456"));
		// Output => $2a$10$7pRt9WcHlpF5qp61Xf6KtecTfubAE4sTt5OZjBt7pSFaI4618Ic22
	}*/
	
}
