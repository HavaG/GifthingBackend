package HavaG.Gifthing.Controller.User

import HavaG.Gifthing.Models.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository) : IUserService {

    override fun getAllUser(): MutableIterable<User> {
        return userRepository.findAll()
    }

    override fun getUserById(userId: Long): Optional<User> {
        return userRepository.findById(userId)
    }

    override fun addUser(user: User): Boolean {
        for(i in userRepository.findAll()) {
            //Already exist
            if(i.name == user.name) {
                return false
            }
        }

        userRepository.save(user)
        return true
    }

    //TODO: updateUser nem jól működik
    override fun updateUser(user: User): Boolean {
        val updateId = user.id
        //check if exist in db
        val tmp = userRepository.findById(updateId)
        if(tmp.isPresent) {
            //delete old
            userRepository.deleteById(tmp.get().id)
            //save new
            userRepository.save(user)
            //set old user id
            user.id = tmp.get().id
            return true
        }
        else
            return false
    }

    override fun deleteUser(userId: Long) {
        val tmp = getUserById(userId)
        if (tmp.isPresent) {
            userRepository.delete(tmp.get())
        }
    }

}