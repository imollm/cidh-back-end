package edu.uoc.hagendazs.macadamianut.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class HttpSecurityConfig() : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // Ping health-check
            .antMatchers(HttpMethod.GET, "/api/v1/ping").permitAll()
            // USER EndPoints
            .antMatchers(HttpMethod.GET, "/api/v1/users/*").authenticated()
            .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
            .antMatchers(HttpMethod.POST,"/api/v1/users/login").permitAll()

            .antMatchers(HttpMethod.POST, "/api/v1/users/refresh-token").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/users/*/verify/*").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/users/credentials/start-password-reset").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/users/*/lifecycle/send-verification-email").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/users/*/credentials/change-password").permitAll()
            // CATEGORIES EndPoints
            .antMatchers(HttpMethod.POST, "/api/v1/categories").authenticated()
            .antMatchers(HttpMethod.PUT, "api/v1/categories/*").authenticated()
            .antMatchers(HttpMethod.GET, "/api/v1/categories/*").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
            // EVENT ORGANIZERS EndPoints
            .antMatchers(HttpMethod.POST, "/api/v1/event-organizer").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/v1/event-organizer/*").authenticated()
            .antMatchers(HttpMethod.GET, "/api/v1/event-organizer/*").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/event-organizer").permitAll()
            // LABELS EndPoints
            .antMatchers(HttpMethod.POST, "/api/v1/labels").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/v1/labels/*").authenticated()
            .antMatchers(HttpMethod.GET, "/api/v1/labels/*").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/labels").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/v1/labels/*").authenticated()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer(fun(obj: OAuth2ResourceServerConfigurer<HttpSecurity?>) {
                obj.accessDeniedHandler { _, response, _ ->
                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN
                    )
                }
                obj.jwt().jwtAuthenticationConverter(this.getJwtAuthConverter())
            })
    }

    private fun getJwtAuthConverter(): Converter<Jwt, out AbstractAuthenticationToken> {

        return object : JwtAuthenticationConverter(), Converter<Jwt, AbstractAuthenticationToken> {
            override fun extractAuthorities(jwt: Jwt?): MutableCollection<GrantedAuthority> {
                val jwtAuthoritiesBytes = jwt?.claims?.get(JwtConstants.AUTHORITIES)
                val jwtAuthoritiesList = jwtAuthoritiesBytes.toString().split(",")
                return jwtAuthoritiesList.map { SimpleGrantedAuthority("ROLE_$it") }.toCollection(ArrayList())
            }
        }
    }

}