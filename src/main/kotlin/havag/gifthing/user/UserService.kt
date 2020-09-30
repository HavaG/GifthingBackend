package havag.gifthing.user

import havag.gifthing.models.user.dto.UserRequest
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.GiftRepository
import havag.gifthing.repositories.TeamRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository,
                  val giftRepository: GiftRepository,
                  val teamRepository: TeamRepository,
                  val userService: UserDetailsProvider
) : IUserService {

    override fun findAll(): MutableIterable<UserResponse> {
        val tmpUsers = userRepository.findAll()
        val response = mutableListOf<UserResponse>()
        for(i in tmpUsers) {
            response.add(i.toUserResponse())
        }
        return response
    }

    override fun findById(userId: Long): UserResponse?  {

        val tmpUser = userRepository.findById(userId)
        if(!tmpUser.isPresent){
            return null
        }
        return tmpUser.get().toUserResponse()
    }

    override fun findByEmail(userEmail: String): UserResponse? {

        val tmpUser = userRepository.findByEmail(userEmail)
        if(!tmpUser.isPresent){
            return null
        }
        return tmpUser.get().toUserResponse()
    }

    override fun findByUsername(username: String): UserResponse? {
        val tmpUser = userRepository.findByUsername(username)
        if(!tmpUser.isPresent){
            return null
        }
        return tmpUser.get().toUserResponse()
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
        val users = this.findAll()
        val usernames: ArrayList<String> = ArrayList<String>()
        users.forEach {
            usernames.add(it.username!!)
        }
        return usernames
    }

    override fun create(user: UserRequest): UserResponse? {
        for(i in userRepository.findAll()) {
            if(i.email == user.email) {
                return null
            }
        }
        val result = user.toUser(giftRepository, teamRepository)
        userRepository.save(result)
        return result.toUserResponse()
    }

    override fun update(user: UserRequest): UserResponse? {
        val tmp = userRepository.findById(user.id)
        return if(tmp.isPresent) {
            val currentUser = userService.getUser()
            if(tmp.get().id == currentUser.id) {
                val saveUser = user.toUser(giftRepository, teamRepository)
                val result = userRepository.save(saveUser)
                result.toUserResponse()
            } else null
        } else null
    }
}