package exec.oauth2.oauth2jwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2JwtApplication

fun main(args: Array<String>) {
    runApplication<OAuth2JwtApplication>(*args)
}
