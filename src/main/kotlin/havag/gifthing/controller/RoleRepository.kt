package havag.gifthing.controller

import havag.gifthing.models.ERole
import havag.gifthing.models.Role
import havag.gifthing.models.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : CrudRepository<Role, Long> {
	fun findByName(name: ERole?): Optional<Role>
}