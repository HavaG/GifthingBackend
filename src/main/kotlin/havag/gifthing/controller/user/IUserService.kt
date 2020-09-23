package havag.gifthing.controller.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse

interface IUserService {

    fun getAllUser(): MutableIterable<UserResponse>
    fun getUserByEmail(userEmail: String): UserResponse?
    fun getUserById(userId: Long): UserResponse?
    fun createUser(user: UserRequest): UserResponse?
    fun updateUser(user: UserRequest): UserResponse?
    fun deleteUser(userId: Long): Boolean
}