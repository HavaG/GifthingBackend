package HavaG.Gifthing.models.team.dto

import HavaG.Gifthing.models.user.User
import HavaG.Gifthing.models.user.dto.UserResponse

class TeamResponse(
    var name: String,
    var id: Long,
    var admin: Long){
    var members = mutableListOf<UserResponse>()

    fun getTeamMembers(): MutableList<UserResponse> {
        return members
    }

    fun setTeamMembers(members: MutableList<User>) {
        val newMembers = mutableListOf<UserResponse>()
        for (member in members) {
            newMembers.add(member.toUserResponse())
        }
        this.members = newMembers
    }
}