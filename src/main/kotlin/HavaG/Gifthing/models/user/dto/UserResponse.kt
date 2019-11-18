package HavaG.Gifthing.models.user.dto

import HavaG.Gifthing.models.user.dto.GiftUserResponse
import HavaG.Gifthing.models.user.dto.TeamUserResponse

class UserResponse(
        var email: String,
        var password: String,
        var id: Long,
        var name: String? = null
) {

    private var gifts = mutableListOf<GiftUserResponse>()

    fun getGifts(): MutableList<GiftUserResponse> {
        return gifts
    }

    fun setGifts(gifts: MutableList<GiftUserResponse>) {
        this.gifts = gifts
    }

    private var reservedGifts = mutableListOf<GiftUserResponse>()

    fun getReservedGifts(): MutableList<GiftUserResponse> {
        return reservedGifts
    }

    fun setReservedGifts(reservedGifts: MutableList<GiftUserResponse>) {
        this.reservedGifts = reservedGifts
    }

    private var myOwnedTeams = mutableListOf<TeamUserResponse>()

    fun getMyOwnedTeams(): MutableList<TeamUserResponse> {
        return myOwnedTeams
    }

    fun setMyOwnedTeams(ownedTeams: MutableList<TeamUserResponse>) {
        this.myOwnedTeams = ownedTeams
    }

    private var myTeams = mutableListOf<TeamUserResponse>()

    fun getMyTeams(): MutableList<TeamUserResponse> {
        return myTeams
    }

    fun setMyTeams(myTeams: MutableList<TeamUserResponse>) {
        this.myTeams = myTeams
    }

}