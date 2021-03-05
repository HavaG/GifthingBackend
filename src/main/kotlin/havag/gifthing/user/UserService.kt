package havag.gifthing.user

import havag.gifthing.models.user.User
import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.GiftRepository
import havag.gifthing.repositories.TeamRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.services.UserDetailsImpl
import havag.gifthing.security.services.UserDetailsProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserService : IUserService {
	@Autowired
	lateinit var userRepository: UserRepository

	@Autowired
	lateinit var userService: UserDetailsProvider

	val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

	override fun findById(userId: Long): UserResponse? {
		val tmpUser = userRepository.findById(userId)
		if (tmpUser.isPresent) {
			val user = tmpUser.get()
			logger.info("UserId ${userService.getUser().id} get userId ${user.id} by id")
			return user.toUserResponse()
		}
		logger.info("Requested user is not exists with userId $userId (request userId ${userService.getUser().id})")
		return null
	}

	override fun findByUsername(username: String): UserResponse? {
		val tmpUser = userRepository.findByUsername(username)
		if (tmpUser.isPresent) {
			val user = tmpUser.get()
			logger.info("UserId ${userService.getUser().id} get userId ${user.id} by username")
			return user.toUserResponse()
		}
		logger.info("Requested user is not exists userId ${userService.getUser().id}")
		return null
	}

	override fun getUsernames(): MutableIterable<String> {
		val users = userRepository.findAll()
		val usernames: ArrayList<String> = ArrayList()
		users.forEach {
			usernames.add(it.username)
		}
		logger.info("UserId ${userService.getUser().id} get all username")
		return usernames
	}

	fun me() : UserDetailsImpl? {
		return userService.getUserDetail()
	}

	override fun delete(userId: Long): Boolean {
		val tmp = userRepository.findById(userId)
		if (tmp.isPresent) {
			val user: User = tmp.get()
			userRepository.delete(user)
			return true
		}
		return false
	}

	/*
	override fun update(user: UserRequest): UserResponse? {
		val tmp = userRepository.findById(user.id)
		val currentUser = userService.getUser()
		return if (tmp.isPresent) {
			if (tmp.get().id == currentUser.id) {
				val saveUser = user.toUser(giftRepository, teamRepository)
				val result = userRepository.save(saveUser)
				logger.info("UserId ${currentUser.id} updated userId ${result.id}")
				result.toUserResponse()
			} else {
				logger.info("Update user failed userId ${currentUser.id} (not own account)")
				null
			}
		} else {
			logger.info("Update user failed userId ${currentUser.id} teamId ${user.id}")
			null
		}
	}

	override fun findAll(): MutableIterable<UserResponse> {
		val tmpUsers = userRepository.findAll()
		val response = mutableListOf<UserResponse>()
		for (i in tmpUsers) {
			response.add(i.toUserResponse())
		}
		logger.info("UserId ${userService.getUser().id} get all users")
		return response
	}

	override fun findByEmail(userEmail: String): UserResponse? {
		val tmpUser = userRepository.findByEmail(userEmail)
		if (tmpUser.isPresent) {
			val user = tmpUser.get()
			logger.info("UserId ${userService.getUser().id} get userId ${user.id} by email")
			return user.toUserResponse()
		}
		logger.info("Requested user is not exists userId ${userService.getUser().id}")
		return null
	}
	 */
}
