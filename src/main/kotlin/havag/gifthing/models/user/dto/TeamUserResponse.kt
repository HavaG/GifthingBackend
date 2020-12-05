package havag.gifthing.models.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TeamUserResponse(
	@JsonProperty("adminId")
	var adminId: Long,
	@JsonProperty("name")
	var name: String,
	@JsonProperty("id")
	var id: Long,
	@JsonProperty("memberIds")
	var memberIds: MutableList<Long>,
	@JsonProperty("lastUpdate")
	var lastUpdate: Long
)