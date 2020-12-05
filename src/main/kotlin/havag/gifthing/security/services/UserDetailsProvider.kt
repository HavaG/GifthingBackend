package havag.gifthing.security.services

import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserDetailsProvider(private val userRepository: UserRepository) {

	fun getUser(): UserResponse {
		val tmpUser = userRepository.findById(getUserDetail()!!.id)
		if (getUserDetail() == null || tmpUser.isEmpty) throw RuntimeException("USER_IS_NOT_LOGGED_IN")
		return tmpUser.get().toUserResponse()
	}


	fun getUserDetail(): UserDetailsImpl? {
		return if (SecurityContextHolder.getContext()?.authentication?.principal is String) {
			null
		} else {
			SecurityContextHolder.getContext()?.authentication?.principal as UserDetailsImpl?
		}
	}
}
