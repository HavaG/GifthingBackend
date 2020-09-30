package havag.gifthing.models.team.dto

import havag.gifthing.models.team.Team
import havag.gifthing.models.user.dto.UserResponse
import havag.gifthing.repositories.UserRepository

class TeamRequest(
        var name: String,
        var id: Long,
        var adminId: Long
) {

    var members = mutableListOf<UserResponse>()

    fun getTeamMembers(): MutableList<UserResponse> {
        return members
    }

    fun setTeamMembers(members: MutableList<UserResponse>) {
        this.members = members
    }

    fun toTeam(userRepository: UserRepository): Team {
        val result = Team(name = this.name)
        result.id = this.id
        val admin = userRepository.findById(this.adminId)
        result.setAdmin(admin.get())
        this.members.forEach {
            val tmp = userRepository.findById(it.id)
            result.addMember(tmp.get())
        }
        return result
    }
}