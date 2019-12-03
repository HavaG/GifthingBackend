package HavaG.Gifthing.models.team.dto

import HavaG.Gifthing.controller.user.UserRepository
import HavaG.Gifthing.models.team.Team

class TeamRequest(
        var name: String,
        var id: Long,
        var adminId: Long
) {

    var members = mutableListOf<Long>()

    fun getTeamMembers(): MutableList<Long> {
        return members
    }

    fun setTeamMembers(members: MutableList<Long>) {
        this.members = members
    }

    fun toTeam(userRepository: UserRepository): Team {
        val result = Team(name = this.name)
        result.id = this.id
        val admin = userRepository.findById(this.adminId)
        result.setAdmin(admin.get())
        this.members.forEach {
            val tmp = userRepository.findById(it)
            result.addMember(tmp.get())
        }
        return result
    }
}