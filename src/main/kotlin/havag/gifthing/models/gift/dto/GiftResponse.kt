package havag.gifthing.models.gift.dto

import com.fasterxml.jackson.annotation.JsonProperty
import havag.gifthing.models.user.dto.GiftUserResponse

class GiftResponse(
	@JsonProperty("id")
	var id: Long,
	@JsonProperty("name")
	var name: String,
	@JsonProperty("owner")
	var owner: GiftUserResponse? = null,
	@JsonProperty("link")
	var link: String? = null,
	@JsonProperty("description")
	var description: String? = null,
	@JsonProperty("price")
	var price: Int? = null,
	@JsonProperty("reservedBy")
	var reservedBy: GiftUserResponse? = null,
	@JsonProperty("lastUpdate")
	var lastUpdate: Long? = null
)