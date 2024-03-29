package dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val userDto:UserDto
): OAuth2User {
    override fun getAttributes(): MutableMap<String, Any>? {
        return null
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val collection: MutableCollection<GrantedAuthority> = ArrayList()
        collection.add(GrantedAuthority {
            userDto.role
        })
        return collection
    }

    override fun getName(): String {
        return userDto.name
    }

    fun getUsername(): String {
        return userDto.name
    }
}

//class CustomOAuth2User(userDTO: UserDTO) : OAuth2User {
//    private val userDTO: UserDTO = userDTO
//
//    override fun getAttributes(): Map<String, Any> {
//        return null
//    }
//
//    override fun getAuthorities(): Collection<GrantedAuthority> {
//        val collection: MutableCollection<GrantedAuthority> = java.util.ArrayList()
//
//        collection.add(GrantedAuthority { userDTO.getRole() })
//
//        return collection
//    }
//
//    override fun getName(): String {
//        return userDTO.getName()
//    }
//
//    val username: String
//        get() = userDTO.getUsername()
//}
