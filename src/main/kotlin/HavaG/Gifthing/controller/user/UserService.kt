package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.user.dto.GiftUserResponse
import HavaG.Gifthing.models.user.dto.TeamUserResponse
import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository) : IUserService {

    override fun getUserByEmail(userEmail: String): Optional<User> {
        return userRepository.findByEmail(userEmail)
    }

    override fun getAllUser(): MutableIterable<UserResponse> {
        val tmpUsers = userRepository.findAll()
        val response = mutableListOf<UserResponse>()
        for(i in tmpUsers) {
            response.add(i.userToUserResponse())
        }
        return response
    }

    override fun getUserById(userId: Long): Optional<User> {
        return userRepository.findById(userId)
    }

    override fun addUser(user: User): Boolean {
        for(i in userRepository.findAll()) {
            //Already exist
            if(i.email == user.email) {
                return false
            }
        }
        userRepository.save(user)
        return true
    }

    override fun updateUser(user: User): Boolean {
        //check if exist in db
        val tmp = userRepository.findById(user.id)
        return if(tmp.isPresent) {
            //save new
            userRepository.save(user)
            true
        }
        else
            false
    }

    //TODO: if a user is an admin in any team, then it cant be deleted
    override fun deleteUser(userId: Long): Boolean {
        val tmp = getUserById(userId)
        if (tmp.isPresent) {
            val temporal = tmp.get()
            if(temporal.getMyOwnedTeams().isNotEmpty()) {
                return false
            } else {
                //remove all the reserved gifts
                temporal.removeAllReservedGift()
                //remove all my gifts from other reserved lists, and all the gifts
                temporal.removeAllGifts()
                //remove from teams
                temporal.removeFromAllTeam()
                //update
                updateUser(temporal)
                //delete
                userRepository.delete(temporal)
                return true
            }
        }
        return false
    }
}