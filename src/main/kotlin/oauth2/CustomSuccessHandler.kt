package oauth2

import dto.CustomOAuth2User
import io.jsonwebtoken.io.IOException
import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jwt.JWTUtil
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomSuccessHandler(
    private val jwtUtil: JWTUtil
): SimpleUrlAuthenticationSuccessHandler() {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        //OAuth2User
        val customUserDetails: CustomOAuth2User = authentication?.principal as CustomOAuth2User
        val username:String = customUserDetails.getUsername()

        val authorities:MutableCollection<out GrantedAuthority> = authentication.authorities
        val iterator:Iterator<GrantedAuthority> = authorities.iterator()
        val auth: GrantedAuthority = iterator.next()
        val role:String = auth.authority

        val token:String = jwtUtil.createJwt("Authorization", role, 60*60*60L)

        response?.addCookie(Cookie("", token))
        response?.sendRedirect("http://localhost:3000/")
    }

    private fun createCookie(key:String, value:String):Cookie{
        val cookie:Cookie = Cookie(key, value)
        cookie.maxAge = 60*60*60*60
        cookie.path = "/"
        cookie.isHttpOnly = true

        return cookie
    }
}


//@Component
//class CustomSuccessHandler(private val jwtUtil: JWTUtil) : SimpleUrlAuthenticationSuccessHandler() {
//    @Throws(java.io.IOException::class, ServletException::class)
//    override fun onAuthenticationSuccess(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        authentication: Authentication
//    ) {
//        //OAuth2User
//
//        val customUserDetails = authentication.principal as CustomOAuth2User
//
//        val username = customUserDetails.getUsername()
//
//        val authorities = authentication.authorities
//        val iterator: Iterator<GrantedAuthority> = authorities.iterator()
//        val auth = iterator.next()
//        val role = auth.authority
//
//        val token = jwtUtil.createJwt(username, role, 60 * 60 * 60 * 60L)
//
//        response.addCookie(createCookie("Authorization", token))
//        response.sendRedirect("http://localhost:3000/")
//    }
//
//    private fun createCookie(key: String, value: String): Cookie {
//        val cookie = Cookie(key, value)
//        cookie.maxAge = 60 * 60 * 60 * 60
//        //cookie.setSecure(true);
//        cookie.path = "/"
//        cookie.isHttpOnly = true
//
//        return cookie
//    }
//}