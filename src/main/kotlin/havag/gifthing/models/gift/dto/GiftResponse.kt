package havag.gifthing.models.gift.dto

import havag.gifthing.models.user.dto.GiftUserResponse

class GiftResponse(
	var id: Long,
	var name: String,
	var owner: GiftUserResponse? = null,
	var link: String? = null,
	var description: String? = null,
	var price: Int? = null,
	var reservedBy: GiftUserResponse? = null,
	var lastUpdate: Long? = null
)