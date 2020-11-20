package havag.gifthing.models.user.dto

class TeamUserResponse(
	var adminId: Long,
	var name: String,
	var id: Long,
	var memberIds: MutableList<Long>,
	var lastUpdate: Long
)