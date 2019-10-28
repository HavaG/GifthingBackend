package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Team
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService(val groupRepository: GroupRepository) : IGroupService {

    //TODO: itt mindegyiknek avn értelme? így kell őket használni? :D
    override fun getAllGroup(): MutableIterable<Team> {
        return groupRepository.findAll()
    }

    override fun getGroupById(groupId: Long): Optional<Team> {
        return groupRepository.findById(groupId)
    }

    override fun addGroup(team: Team): Boolean {
        groupRepository.save(team)
        return true
    }

    //TODO: updateGroup
    override fun updateGroup(team: Team): Boolean {
        return false
    }

    override fun deleteGroup(groupId: Long) {
        val tmp = getGroupById(groupId)
        if (tmp.isPresent) {
            groupRepository.delete(tmp.get())
        }
    }
}