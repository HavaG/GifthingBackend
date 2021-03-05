package havag.gifthing.authentication.payload.response

import com.fasterxml.jackson.annotation.JsonProperty

class JwtResponse(
	@JsonProperty("accessToken")
	var accessToken: String,
	@JsonProperty("id")
	var id: Long,
	@JsonProperty("username")
	var username: String,
	@JsonProperty("email")
	var email: String,
	@JsonProperty("roles")
	val roles: List<String>
) {
	@JsonProperty("tokenType")
	var tokenType = "Bearer"
}