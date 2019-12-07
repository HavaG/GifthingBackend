package HavaG.Gifthing.controller.team

import HavaG.Gifthing.controller.user.UserRepository
import HavaG.Gifthing.models.team.Team
import HavaG.Gifthing.models.team.dto.TeamRequest
import HavaG.Gifthing.models.team.dto.TeamResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeamService(val teamRepository: TeamRepository,
                  val userRepository: UserRepository) : ITeamService {

    //TODO: probaly not working
    override fun addUser(groupId: Long, userId: Long): TeamResponse? {
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

    override fun getAllTeam(): MutableIterable<TeamResponse> {
        val teams = teamRepository.findAll()
        val result = mutableListOf<TeamResponse>()
        for (i in teams) {
            result.add(i.toTeamResponse())
        }
        return result
    }

    override fun getTeamById(teamId: Long): TeamResponse? {
        val tmpTeam = teamRepository.findById(teamId)
        if(!tmpTeam.isPresent){
            return null
        }
        return tmpTeam.get().toTeamResponse()
    }

    override fun createTeam(team: TeamRequest): TeamResponse {
        val saveTeam = team.toTeam(userRepository)
        val result = teamRepository.save(saveTeam)
        return result.toTeamResponse()
    }

    override fun updateTeam(team: TeamRequest): TeamResponse? {
        val tmp = teamRepository.findById(team.id)
        if(tmp.isPresent) {
            val saveTeam = team.toTeam(userRepository)
            val result = teamRepository.save(saveTeam)
            return result.toTeamResponse()
        }
        return null
    }

    override fun deleteTeam(teamId: Long): Boolean {
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