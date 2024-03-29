package jwt

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@Component
class JWTUtil(@Value("\${spring.jwt.secret}") secret:String){
    // 시크릿 키를 UTF-8 바이트 배열로 변환해서, HS256 암호화
    private val secretKey: SecretKey =
        SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)

    // 토큰의 페이로드에서 사용자명 반환하는 함수
    fun getUsername(token:String):String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get("username", String::class.java)
    }

    // 토큰에서
    fun getRole(token:String):String {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get("role", String::class.java)
    }

    // 토큰 페이로드에서 만료 시간을 가져와 현재시간과 비교하여, 만료되지 않았다면 false 반환
    fun isExpired(token:String):Boolean {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.expiration.before(Date())
    }

    // 사용자명, 역할, 만료시간(밀리세컨드 단위)를 가지고 토큰 생성, 그리고 비밀키를 통해 서명
    fun createJwt(username:String, role:String, expiredMs:Long): String{
       return Jwts.builder()
           .claim("username", username)
           .claim("role", role)
           .issuedAt(Date(System.currentTimeMillis()))
           .expiration(Date(System.currentTimeMillis() + expiredMs))
           .signWith(secretKey)
           .compact()
    }

}

// Jetbrain auto translate codes
//@Component
//class JWTUtil(@Value("\${spring.jwt.secret}") secret: String) {
//    private val secretKey: SecretKey =
//        SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)
//
//    fun getUsername(token: String?): String {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
//            "username",
//            String::class.java
//        )
//    }
//
//    fun getRole(token: String?): String {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.get(
//            "role",
//            String::class.java
//        )
//    }
//
//    fun isExpired(token: String?): Boolean {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.expiration.before(Date())
//    }
//
//    fun createJwt(username: String?, role: String?, expiredMs: Long): String {
//        return Jwts.builder()
//            .claim("username", username)
//            .claim("role", role)
//            .issuedAt(Date(System.currentTimeMillis()))
//            .expiration(Date(System.currentTimeMillis() + expiredMs))
//            .signWith(secretKey)
//            .compact()
//    }
//}
//
