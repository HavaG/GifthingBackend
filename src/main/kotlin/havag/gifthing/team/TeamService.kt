package havag.gifthing.team

import havag.gifthing.repositories.UserRepository
import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse
import havag.gifthing.repositories.TeamRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.springframework.stereotype.Service

@Service
class TeamService(val teamRepository: TeamRepository,
                  val userRepository: UserRepository,
                  val userService: UserDetailsProvider
) : ITeamService {

    //TODO: probaly not working
    override fun addMember(groupId: Long, userId: Long): TeamResponse? {
        val group = teamRepository.findById(groupId)
        val user = userRepository.findById(userId)
        if(user.isPresent && group.isPresent) {
            val saveGroup = group.get()
            saveGroup.addMember(user.get())
            val result = teamRepository.save(saveGroup)
            return result.toTeamResponse()
        } else {
            return null
        }
    }

    override fun removeMember(groupId: Long, userId: Long): TeamResponse? {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableIterable<TeamResponse> {
        val teams = teamRepository.findAll()
        val result = mutableListOf<TeamResponse>()
        for (i in teams) {
            result.add(i.toTeamResponse())
        }
        return result
    }

    override fun findById(teamId: Long): TeamResponse? {
        val tmpTeam = teamRepository.findById(teamId)
        return if(tmpTeam.isPresent){
            val team = tmpTeam.get()
            var member = false
            val myTeams = userService.getUser().getMyTeams()
            myTeams.forEach{
                if(it.id == team.id)
                    member = true
            }
            if(member)
                team.toTeamResponse()
            else null
        } else null
    }

    override fun create(team: TeamRequest): TeamResponse {
        val saveTeam = team.toTeam(userRepository)
        val result = teamRepository.save(saveTeam)
        return result.toTeamResponse()
    }

    override fun update(team: TeamRequest): TeamResponse? {
        val tmp = teamRepository.findById(team.id)
        return if(tmp.isPresent) {
            val myOwnedTeams = userService.getUser().getMyOwnedTeams()
            var owned = false
            myOwnedTeams.forEach {
                if(it.id == team.id)
                    owned = true
            }
            return if(owned) {
                val saveTeam = team.toTeam(userRepository)
                val result = teamRepository.save(saveTeam)
                result.toTeamResponse()
            } else null
        } else null
    }

    override fun delete(teamId: Long): Boolean {
        //TODO: not implemented
        return false
        /*val tmp = teamRepository.findById(teamId)
        if (tmp.isPresent) {
            val temporal = tmp.get()
            if(temporal.members.size == 1) {
                val temporalMemberId = temporal.members[0].id
                val adminId = temporal.getAdmin()?.id
                if(adminId == null) {
                    teamRepository.delete(temporal)
                    return true
                } else if(adminId == temporalMemberId) {
                    temporal.removeAdmin()
                    temporal.removeAllMember()
                    teamRepository.delete(temporal)
                    return true
                } else {
                    return false
                }
            }
        }
        return false*/
    }
}