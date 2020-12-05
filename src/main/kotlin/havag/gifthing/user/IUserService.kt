package havag.gifthing.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse
import org.springframework.http.HttpStatus

interface IUserService {
	fun findById(userId: Long): UserResponse?
	fun findByUsername(username: String): UserResponse?
	fun delete(userId: Long): Boolean
	fun getUsernames(): MutableIterable<String>
	/*
	fun findAll(): MutableIterable<UserResponse>
	fun update(user: UserRequest): UserResponse?
	fun findByEmail(userEmail: String): UserResponse?
	 */
}