package havag.gifthing.models.user.dto

import havag.gifthing.controller.gift.GiftRepository
import havag.gifthing.controller.team.TeamRepository
import havag.gifthing.models.gift.Gift
import havag.gifthing.models.team.Team
import havag.gifthing.models.user.User

class UserRequest(
        var email: String,
        var password: String,
        var id: Long,
        var firstName: String,
        var lastName: String,
        var username: String?
) {
    private var giftsId = mutableListOf<Long>()

    private var reservedGiftsId = mutableListOf<Long>()

    private var myOwnedTeamsId = mutableListOf<Long>()

    private var myTeamsId = mutableListOf<Long>()

    fun toUser(giftRepository: GiftRepository, teamRepository: TeamRepository): User {
        val result = User(this.username!!, this.email, this.password)
        result.id = this.id
        val giftList = mutableListOf<Gift>()
        val teamList = mutableListOf<Team>()
        for (i in giftsId) {
            val tmp = giftRepository.findById(i)
            giftList.add(tmp.get())
        }
        result.setGifts(giftList)
        giftList.clear()
        for (i in reservedGiftsId) {
            val tmp = giftRepository.findById(i)
            giftList.add(tmp.get())
        }
        result.setReservedGifts(giftList)
        giftList.clear()
        for (i in myOwnedTeamsId) {
            val tmp = teamRepository.findById(i)
            teamList.add(tmp.get())
        }
        result.setMyOwnedTeams(teamList)
        teamList.clear()
        for (i in myTeamsId) {
            val tmp = teamRepository.findById(i)
            teamList.add(tmp.get())
        }
        result.setMyTeams(teamList)
        teamList.clear()

        return result
    }
}
