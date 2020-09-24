package havag.gifthing.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse

interface IUserService {
	fun findAll(): MutableIterable<UserResponse>
	fun findByEmail(userEmail: String): UserResponse?
	fun findById(userId: Long): UserResponse?
	fun create(user: UserRequest): UserResponse?
	fun update(user: UserRequest): UserResponse?
	fun delete(userId: Long): Boolean
}