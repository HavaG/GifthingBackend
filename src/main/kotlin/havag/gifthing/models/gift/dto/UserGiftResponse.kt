package havag.gifthing.models.gift.dto

class UserGiftResponse(
	var email: String,
	var password: String,
	var id: Long = 0,
	var firstName: String,
	var lastName: String,
	var username: String? = null
)