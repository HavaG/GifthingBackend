package havag.gifthing.models.team.dto

import com.fasterxml.jackson.annotation.JsonProperty
import havag.gifthing.models.user.User
import havag.gifthing.models.user.dto.UserResponse

class TeamResponse(
	@JsonProperty("name")
	var name: String,
	@JsonProperty("id")
	var id: Long,
	@JsonProperty("adminId")
	var adminId: Long,
	@JsonProperty("lastUpdate")
	var lastUpdate: Long
) {
	@JsonProperty("members")
	private var members = mutableListOf<UserResponse>()

	fun getMembers(): MutableList<UserResponse> {
		return members
	}

	fun setMembers(members: MutableList<User>) {
		val newMembers = mutableListOf<UserResponse>()
		for (member in members) {
			newMembers.add(member.toUserResponse())
		}
		this.members = newMembers
	}
}