package jwt

import dto.CustomOAuth2User
import dto.UserDto
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JWTFilter (
    private val jwtUtil: JWTUtil
): OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var authorization: String? = null
        // cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        val cookies:Array<out Cookie> = request.cookies
        for (cookie in cookies) {
            println(cookie.name)
            if (cookie.name == "Authorization") {
                authorization = cookie.value
            }
        }

        // Authorization header check
        if (authorization == null) {
            println("token null")
            filterChain.doFilter(request, response)

            // mehthod close
            return
        }

        // Token
        val token: String = authorization

        // Token expiration time check
        if (jwtUtil.isExpired(token)) {
            println("token expired")
            filterChain.doFilter(request, response)

            // quit method
            return
        }

        // get username and role on token
        val username: String = jwtUtil.getUsername(token)
        val role: String = jwtUtil.getRole(token)

        // create userDTO and set role
        val userDto: UserDto = UserDto(role, "", username)

        // put member info on UserDetails
        val customOAuth2User: CustomOAuth2User = CustomOAuth2User(userDto)

        // generate spring security authentication token
        val authToken: Authentication =
            UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.authorities)
        filterChain.doFilter(request, response)
    }
}