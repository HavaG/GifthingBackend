package HavaG.Gifthing.models.user.dto

import HavaG.Gifthing.controller.gift.GiftRepository
import HavaG.Gifthing.controller.team.TeamRepository
import HavaG.Gifthing.models.gift.Gift
import HavaG.Gifthing.models.team.Team
import HavaG.Gifthing.models.user.User

class UserRequest(
        var email: String,
        var password: String,
        var id: Long,
        var firstName: String,
        var lastName: String,
        var nickName: String?
) {
    private var giftsId = mutableListOf<Long>()

    private var reservedGiftsId = mutableListOf<Long>()

    private var myOwnedTeamsId = mutableListOf<Long>()

    private var myTeamsId = mutableListOf<Long>()

    fun toUser(giftRepository: GiftRepository, teamRepository: TeamRepository): User {
        val result = User(this.email, this.password, this.firstName, this.lastName)
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
