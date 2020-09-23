package HavaG.Gifthing.controller.user

import HavaG.Gifthing.controller.gift.GiftRepository
import HavaG.Gifthing.controller.team.TeamRepository
import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserRequest
import HavaG.Gifthing.models.user.dto.UserResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository,
                  val giftRepository: GiftRepository,
                  val teamRepository: TeamRepository) : IUserService {

    override fun getAllUser(): MutableIterable<UserResponse> {
        val tmpUsers = userRepository.findAll()
        val response = mutableListOf<UserResponse>()
        for(i in tmpUsers) {
            response.add(i.toUserResponse())
        }
        return response
    }

    override fun getUserById(userId: Long): UserResponse?  {

        val tmpUser = userRepository.findById(userId)
        if(!tmpUser.isPresent){
            return null
        }
        return tmpUser.get().toUserResponse()
    }

    override fun getUserByEmail(userEmail: String): UserResponse? {

        val tmpUser = userRepository.findByEmail(userEmail)
        if(!tmpUser.isPresent){
            return null
        }
        return tmpUser.get().toUserResponse()
    }

    override fun deleteUser(userId: Long): Boolean {
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


    override fun createUser(user: UserRequest): UserResponse? {
        for(i in userRepository.findAll()) {
            if(i.email == user.email) {
                return null
            }
        }
        val result = user.toUser(giftRepository, teamRepository)
        userRepository.save(result)
        return result.toUserResponse()
    }

    override fun updateUser(user: UserRequest): UserResponse? {
        val tmp = userRepository.findById(user.id)
        if(tmp.isPresent) {
            val saveUser = user.toUser(giftRepository, teamRepository)
            val result = userRepository.save(saveUser)
            return result.toUserResponse()
        }
        return null
    }
}