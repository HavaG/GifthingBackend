package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserRequest
import HavaG.Gifthing.models.user.dto.UserResponse
import java.util.*

interface IUserService {

    fun getAllUser(): MutableIterable<UserResponse>
    fun getUserByEmail(userEmail: String): UserResponse?
    fun getUserById(userId: Long): UserResponse?
    fun createUser(user: UserRequest): UserResponse?
    fun updateUser(user: UserRequest): UserResponse?
    fun deleteUser(userId: Long): Boolean
}