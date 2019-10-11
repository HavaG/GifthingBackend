package HavaG.Gifthing.controller

import HavaG.Gifthing.models.User
import org.springframework.context.annotation.Bean
import java.util.*

interface IUserService {

    fun getAllUser(): MutableIterable<User>
    fun getUserById(userId: Long): Optional<User>
    fun addUser(user: User): Boolean
    fun updateUser(user: User): Boolean
    fun deleteUser(userId: Long)
}