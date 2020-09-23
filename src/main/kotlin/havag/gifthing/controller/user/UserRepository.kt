package HavaG.Gifthing.controller.user

import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserResponse
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long>{
    fun findByEmail(email: String): Optional<User>
}