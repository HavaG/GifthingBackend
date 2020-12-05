package havag.gifthing.models.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import havag.gifthing.models.gift.dto.GiftResponse

class UserResponse(
	@JsonProperty("email")
	var email: String,
	@JsonProperty("id")
	var id: Long,
	@JsonProperty("firstName")
	var firstName: String? = null,
	@JsonProperty("lastName")
	var lastName: String? = null,
	@JsonProperty("username")
	var username: String,
	@JsonProperty("lastUpdate")
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

	override fun equals(other: Any?): Boolean {
		other as UserResponse
		if(this.email == other.email &&
			this.id == other.id &&
			this.firstName == other.firstName &&
			this.lastName == other.lastName &&
			this.username == other.username &&
			this.lastUpdate == other.lastUpdate &&
			this.gifts.size == other.getGifts().size &&
			this.reservedGifts.size == other.getReservedGifts().size &&
			this.myOwnedTeams.size == other.getMyOwnedTeams().size &&
			this.myTeams.size == other.getMyTeams().size) {
			gifts.forEach {
				if(!other.getGifts().contains(it))
					return false
			}
			reservedGifts.forEach {
				if(!other.reservedGifts.contains(it))
					return false
			}
			myOwnedTeams.forEach {
				if(!other.myOwnedTeams.contains(it))
					return false
			}
			myTeams.forEach {
				if(!other.getMyTeams().contains(it))
					return false
			}
			return true
		}
		return false
	}
}