package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Group
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService(val groupRepository: GroupRepository) : IGroupService {

    //TODO: itt mindegyiknek avn értelme? így kell őket használni? :D
    override fun getAllGroup(): MutableIterable<Group> {
        return groupRepository.findAll()
    }

    override fun getGroupById(groupId: Long): Optional<Group> {
        return groupRepository.findById(groupId)
    }

    override fun addGroup(group: Group): Boolean {
        groupRepository.save(group)
        return true
    }

    //TODO: updateGroup
    override fun updateGroup(group: Group): Boolean {
        return false
    }

    override fun deleteGroup(groupId: Long) {
        val tmp = getGroupById(groupId)
        if (tmp.isPresent) {
            groupRepository.delete(tmp.get())
        }
    }
}