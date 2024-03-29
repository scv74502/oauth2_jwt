package user.service

import dto.UserDto
import config.security.authorization.model.CustomOAuth2User
import dto.NaverResponse
import dto.OAuth2Response
import entity.User
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import user.respository.UserRepository


@Service
class CustomOAuth2UserService(private val userRepository: UserRepository) : DefaultOAuth2UserService() {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User:OAuth2User = super.loadUser(userRequest)
        println(oAuth2User)

        val registrationId = userRequest?.clientRegistration?.registrationId
        val oAuth2Response: OAuth2Response by lazy {
            NaverResponse(oAuth2User.attributes)
        }
        val userName = oAuth2Response.provider + " " + oAuth2Response.providerId

        // check existing name
        val existData:User? = userRepository.findByUserName(userName)

        if (existData == null) {
            val user:User = User(0, userName, oAuth2Response.name!!, oAuth2Response.email!!, "ROLE_USER")
            userRepository.save(user)
            val userDto: UserDto = UserDto(userName, oAuth2Response.name!!, "ROLE_USER")
            return CustomOAuth2User(userDto)
        }
        else {
            existData.email = oAuth2Response.email!!
            existData.name = oAuth2Response.name!!

            userRepository.save(existData)

            val userDto: UserDto = UserDto(existData.userName, oAuth2Response.name!!, existData.role)
            return CustomOAuth2User(userDto)
        }
    }
}