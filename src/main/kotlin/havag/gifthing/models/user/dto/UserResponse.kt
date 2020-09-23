package havag.gifthing.models.user.dto

class UserResponse(
        var email: String,
        var password: String,
        var id: Long,
        var firstName: String,
        var lastName: String,
        var username: String? = null
) {

    private var gifts = mutableListOf<GiftUserResponse>()
    private var reservedGifts = mutableListOf<GiftUserResponse>()
    private var myOwnedTeams = mutableListOf<TeamUserResponse>()
    private var myTeams = mutableListOf<TeamUserResponse>()

    fun getGifts(): MutableList<GiftUserResponse> {
        return gifts
    }

    fun setGifts(gifts: MutableList<GiftUserResponse>) {
        this.gifts = gifts
    }

    fun getReservedGifts(): MutableList<GiftUserResponse> {
        return reservedGifts
    }

    fun setReservedGifts(reservedGifts: MutableList<GiftUserResponse>) {
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