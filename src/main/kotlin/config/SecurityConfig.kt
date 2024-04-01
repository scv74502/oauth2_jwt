package config

import jwt.JWTUtil
import oauth2.CustomSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import user.service.CustomOAuth2UserService
import java.util.*


//@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customSuccessHandler: CustomSuccessHandler,
    private val jwtUtil: JWTUtil
) {
//    @Bean
//    @Throws(Exception::class)
//    fun filterChaijn(http:HttpSecurity):SecurityFilterChain {
//        http
//            .cors{
//                corsCustomizer -> corsCustomizer.configurationSource { CorsConfigurationSource() }
//    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { corsCustomizer: CorsConfigurer<HttpSecurity?> ->
                corsCustomizer.configurationSource {
                    val configuration = CorsConfiguration()
                    configuration.allowedOrigins = Collections.singletonList("http://localhost:3000")
                    configuration.allowedMethods = Collections.singletonList("*")
                    configuration.allowCredentials = true
                    configuration.allowedHeaders = Collections.singletonList("*")
                    configuration.maxAge = 3600L

                    configuration.exposedHeaders = Collections.singletonList("Set-Cookie")
                    configuration.exposedHeaders = Collections.singletonList("Authorization")

                    return@configurationSource configuration
                }
            }

        return http.build()
    }
}

//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http:HttpSecurity):SecurityFilterChain{
//        // csrf disable
//        http
//            .csrf{auth -> auth.disable()}
//
//        // form login disable
//        http
//            .formLogin { auth -> auth.disable() }
//
//        // http basic authentication disable
//        http
//            .httpBasic{ auth -> auth.disable() }
//
//        // add JWTFilter
//        http
//            .addFilterBefore(JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
//
//        //oauth2
//        http
//            .oauth2Login { oauth2 ->
//                oauth2
//                    .userInfoEndpoint{ userInfoEndpointConfig ->
//                        userInfoEndpointConfig
//                            .userService(customOAuth2UserService)
//                    }
//                    .successHandler(customSuccessHandler)
//            }

//        http
//            .oauth2Login { oauth2 ->
//                oauth2
//                    .userInfoEndpoint { userInfoEndpointConfig ->
//                        userInfoEndpointConfig
//                            .userService(customOAuth2UserService)
//                    }
//                    .successHandler(customSuccessHandler)
//            }
//        // authorize per path
//        http
//            .authorizeHttpRequests{ auth -> auth
//                .requestMatchers("/").permitAll()
//                .anyRequest().authenticated()
//            }
//
//        // session settings : stateless
//        http
//            .sessionManagement { session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
//
//        return http.build()
//    }
//}

//@Configuration
//@EnableWebSecurity
//class SecurityConfig(
//    private val customOAuth2UserService: CustomOAuth2UserService,
//    private val customSuccessHandler: CustomSuccessHandler,
//    private val jwtUtil: JWTUtil
//) {
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .cors { corsCustomizer: CorsConfigurer<HttpSecurity?> ->
//                corsCustomizer.configurationSource {
//                    val configuration = CorsConfiguration()
//                    configuration.allowedOrigins = listOf("http://localhost:3000")
//                    configuration.allowedMethods = listOf("*")
//                    configuration.allowCredentials = true
//                    configuration.allowedHeaders = listOf("*")
//                    configuration.maxAge = 3600L
//
//                    configuration.exposedHeaders = listOf("Set-Cookie")
//                    configuration.exposedHeaders = listOf("Authorization")
//                    return@cors configuration
//                }
//            }
//
//        //csrf disable
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
//        //JWTFilter 추가
//        http
//            .addFilterBefore(JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
//
//        //oauth2
//        http
//            .oauth2Login { oauth2: OAuth2LoginConfigurer<HttpSecurity?> ->
//                oauth2
//                    .userInfoEndpoint(Customizer { userInfoEndpointConfig: UserInfoEndpointConfig ->
//                        userInfoEndpointConfig
//                            .userService(customOAuth2UserService)
//                    })
//                    .successHandler(customSuccessHandler)
//            }
//
//        //경로별 인가 작업
//        http
//            .authorizeHttpRequests(Customizer { auth: AuthorizationManagerRequestMatcherRegistry ->
//                auth
//                    .requestMatchers("/").permitAll()
//                    .requestMatchers("my").hasRole("USER")
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