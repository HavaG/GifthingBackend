package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Group
import java.util.*

interface IGroupService {

    fun getAllGroup(): MutableIterable<Group>
    fun getGroupById(groupId: Long): Optional<Group>
    fun addGroup(group: Group): Boolean
    fun updateGroup(group: Group): Boolean
    fun deleteGroup(groupId: Long)
}