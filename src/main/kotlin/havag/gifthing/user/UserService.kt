package havag.gifthing.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.GiftRepository
import havag.gifthing.repositories.TeamRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(
	val userRepository: UserRepository,
	val giftRepository: GiftRepository,
	val teamRepository: TeamRepository,
	val userService: UserDetailsProvider,
	val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
) : IUserService {

	override fun findAll(): MutableIterable<UserResponse> {
		val tmpUsers = userRepository.findAll()
		val response = mutableListOf<UserResponse>()
		for (i in tmpUsers) {
			response.add(i.toUserResponse())
		}
		logger.info("UserId ${userService.getUser().id} get all users")
		return response
	}

	override fun findById(userId: Long): UserResponse? {
		val tmpUser = userRepository.findById(userId)
		if (tmpUser.isPresent) {
			val user = tmpUser.get()
			logger.info("UserId ${userService.getUser().id} get userId ${user.id} by id")
			return user.toUserResponse()
		}
		logger.info("Requested user is not exists userId ${userService.getUser().id}")
		return null
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

	override fun delete(userId: Long): Boolean {
		//TODO: not implemented
		return false
		/*val tmp = userRepository.findById(userId)
		if (tmp.isPresent) {
			val temporal = tmp.get()
			if(temporal.getMyOwnedTeams().isNotEmpty()) {
				return false
			} else {
				temporal.removeAllReservedGift() //remove all the reserved gifts
				temporal.removeAllGifts() //remove all my gifts from other reserved lists, and all the gifts
				temporal.removeFromAllTeam() //remove from teams
				updateUser(temporal)
				userRepository.delete(temporal)
				return true
			}
		}
		return false*/
	}

	override fun getUsernames(): ArrayList<String> {
		val users = userRepository.findAll()
		val usernames: ArrayList<String> = ArrayList()
		users.forEach {
			usernames.add(it.username)
		}
		logger.info("UserId ${userService.getUser().id} get all username")
		return usernames
	}

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
}