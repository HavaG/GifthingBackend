package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserResponse
import java.util.*

interface IUserService {

    fun getAllUser(): MutableIterable<UserResponse>
    fun getUserById(userId: Long): Optional<User>
    fun addUser(user: User): Boolean
    fun updateUser(user: User): Boolean
    fun deleteUser(userId: Long): Boolean
    fun getUserByEmail(userEmail: String): Optional<User>
}