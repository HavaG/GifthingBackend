package havag.gifthing.security.services

import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.user.UserService
import org.springframework.security.core.context.SecurityContextHolder

class UserDetailsProvider(private val userService: UserService) {

	fun getUser(): UserResponse {
		if (getUserDetail() == null) RuntimeException("USER_IS_NOT_LOGGED_IN")
		return userService.findById(getUserDetail()!!.id)!!
	}

	private fun getUserDetail(): UserDetailsImpl? {
		return if (SecurityContextHolder.getContext()?.authentication?.principal is String) {
			null
		} else {
			SecurityContextHolder.getContext()?.authentication?.principal as UserDetailsImpl?
		}
	}
}
