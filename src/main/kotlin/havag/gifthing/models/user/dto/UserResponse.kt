package havag.gifthing.models.user.dto

import havag.gifthing.models.gift.dto.GiftResponse

class UserResponse(
	var email: String,
	var id: Long,
	var firstName: String? = null,
	var lastName: String? = null,
	var username: String,
	var lastUpdate: Long
) {

	private var gifts = mutableListOf<GiftResponse>()
	private var reservedGifts = mutableListOf<GiftResponse>()
	private var myOwnedTeams = mutableListOf<TeamUserResponse>()
	private var myTeams = mutableListOf<TeamUserResponse>()

	fun getGifts(): MutableList<GiftResponse> {
		return gifts
	}

	fun setGifts(gifts: MutableList<GiftResponse>) {
		this.gifts = gifts
	}

	fun getReservedGifts(): MutableList<GiftResponse> {
		return reservedGifts
	}

	fun setReservedGifts(reservedGifts: MutableList<GiftResponse>) {
		this.reservedGifts = reservedGifts
	}

	fun getMyOwnedTeams(): MutableList<TeamUserResponse> {
		return myOwnedTeams
	}

	fun setMyOwnedTeams(ownedTeams: MutableList<TeamUserResponse>) {
		this.myOwnedTeams = ownedTeams
	}

	fun getMyTeams(): MutableList<TeamUserResponse> {
		return myTeams
	}

	fun setMyTeams(myTeams: MutableList<TeamUserResponse>) {
		this.myTeams = myTeams
	}

}