package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long>{
    //TODO: UserRepository
    fun findByEmail(email: String): Optional<User>
}