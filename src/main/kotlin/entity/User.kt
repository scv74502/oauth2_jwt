package entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name="user")
class User(
    @get:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @get: NotNull
    var userName:String = "",

    @get: NotNull
    var name:String = "",

    var email:String = "",
    var role:String = ""
){

}