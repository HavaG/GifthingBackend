package havag.gifthing.models.user.dto

class GiftUserResponse(
	var name: String,
	var id: Long,
	var link: String? = null,
	var description: String? = null,
	var price: Int? = null,
	var owner: Long
)