package havag.gifthing.repositories

import havag.gifthing.models.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long> {
	fun findByEmail(email: String): Optional<User>

	fun findByUsername(username: String?): Optional<User>

	fun existsByUsername(username: String?): Boolean?

	fun existsByEmail(email: String?): Boolean?
}