package config.security.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.UserInfoEndpointConfig
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val customOAuth2UserService: CustomOAuth2UserService) {

    @Bean
    fun filterChain(http:HttpSecurity):SecurityFilterChain{
        // csrf disable
        http
            .csrf{auth -> auth.disable()}

        // form login disable
        http
            .formLogin { auth -> auth.disable() }

        // http basic authentication disable
        http
            .httpBasic{ auth -> auth.disable() }

        // OAuth2
//        http
//            .oauth2Login{ Customizer.withDefaults<Any?>()}

        //oauth2
        http
            .oauth2Login(Customizer.withDefaults());

        // authorize per path
        http
            .authorizeHttpRequests{ auth -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
            }

        // session settings : stateless
        http
            .sessionManagement { session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return http.build()
    }
}

//@Configuration
//@EnableWebSecurity
//class SecurityConfig(private val customOAuth2UserService: CustomOAuth2UserService) {
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        //csrf disable
//
//        http
//            .csrf { auth: CsrfConfigurer<HttpSecurity> -> auth.disable() }
//
//        //From 로그인 방식 disable
//        http
//            .formLogin { auth: FormLoginConfigurer<HttpSecurity> -> auth.disable() }
//
//        //HTTP Basic 인증 방식 disable
//        http
//            .httpBasic { auth: HttpBasicConfigurer<HttpSecurity> -> auth.disable() }
//
//        //oauth2
//        http
//            .oauth2Login { oauth2: OAuth2LoginConfigurer<HttpSecurity?> ->
//                oauth2
//                    .userInfoEndpoint(Customizer { userInfoEndpointConfig: UserInfoEndpointConfig ->
//                        userInfoEndpointConfig
//                            .userService(customOAuth2UserService)
//                    })
//            }
//
//        //경로별 인가 작업
//        http
//            .authorizeHttpRequests(Customizer { auth: AuthorizationManagerRequestMatcherRegistry ->
//                auth
//                    .requestMatchers("/").permitAll()
//                    .anyRequest().authenticated()
//            })
//
//        //세션 설정 : STATELESS
//        http
//            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
//                session
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
//
//        return http.build()
//    }
//}