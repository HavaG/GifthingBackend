package havag.gifthing.models.team.dto

import havag.gifthing.models.team.Team
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.UserRepository

class TeamRequest(
	var name: String,
	var id: Long?,
	var adminId: Long,
	var members: MutableList<Long>
) {
	fun toTeam(userRepository: UserRepository): Team {
		val result = Team(name = this.name)
		this.id?.let { result.id = it }
		val admin = userRepository.findById(this.adminId)
		result.setAdmin(admin.get())
		this.members.forEach { result.addMember(userRepository.findById(it).get()) }
		return result
	}
}