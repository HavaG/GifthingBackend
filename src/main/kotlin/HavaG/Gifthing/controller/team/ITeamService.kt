package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Team
import java.util.*

interface IGroupService {

    fun getAllGroup(): MutableIterable<Team>
    fun getGroupById(groupId: Long): Optional<Team>
    fun addGroup(team: Team): Boolean
    fun updateGroup(team: Team): Boolean
    fun deleteGroup(groupId: Long)
}