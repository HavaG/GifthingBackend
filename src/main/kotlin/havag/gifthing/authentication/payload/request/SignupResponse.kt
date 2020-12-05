package havag.gifthing.authentication.payload.request

import havag.gifthing.models.user.dto.UserResponse

class SignupResponse (
	var user: UserResponse? = null,
	var message: String? = null
)