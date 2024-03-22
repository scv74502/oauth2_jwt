package config.security.authorization

import config.security.authorization.dto.UserDto
import config.security.authorization.model.CustomOAuth2User
import config.security.authorization.response.NaverResponse
import config.security.authorization.response.OAuth2Response
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


@Service
class CustomOAuth2UserService: DefaultOAuth2UserService() {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User:OAuth2User = super.loadUser(userRequest)
        println(oAuth2User)

        val registrationId = userRequest?.clientRegistration?.registrationId
        val oAuth2Response:OAuth2Response by lazy {
            NaverResponse(oAuth2User.attributes)
        }
        val userName = oAuth2Response.provider + " " + oAuth2Response.providerId
        val userDto: UserDto = UserDto(userName, oAuth2Response.name!!, "ROLE_USER")
        return CustomOAuth2User(userDto)
    }
}