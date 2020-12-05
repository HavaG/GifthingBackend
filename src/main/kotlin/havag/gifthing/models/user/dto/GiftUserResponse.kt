package havag.gifthing.models.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

class GiftUserResponse(
	@JsonProperty("email")
	var email: String,
	@JsonProperty("id")
	var id: Long,
	@JsonProperty("firstName")
	var firstName: String,
	@JsonProperty("lastName")
	var lastName: String,
	@JsonProperty("username")
	var username: String,
	@JsonProperty("lastUpdate")
	var lastUpdate: Long
)
