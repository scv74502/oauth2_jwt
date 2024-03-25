package user.respository

import entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository: JpaRepository<User, Long> {
    fun findByUserName(userName:String): User?
}

