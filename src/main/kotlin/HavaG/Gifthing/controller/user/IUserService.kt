package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.User
import java.util.*

interface IUserService {

    fun getAllUser(): MutableIterable<User>
    fun getUserById(userId: Long): Optional<User>
    fun addUser(user: User): Boolean
    fun updateUser(user: User): Boolean
    fun deleteUser(userId: Long): Boolean
}