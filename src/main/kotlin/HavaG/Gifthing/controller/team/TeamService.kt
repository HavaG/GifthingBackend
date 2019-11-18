package HavaG.Gifthing.controller.team

import HavaG.Gifthing.models.team.Team
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeamService(val teamRepository: TeamRepository) : ITeamService {

    override fun getAllTeam(): MutableIterable<Team> {
        return teamRepository.findAll()
    }

    override fun getTeamById(teamId: Long): Optional<Team> {
        return teamRepository.findById(teamId)
    }

    override fun addTeam(team: Team): Boolean {
        teamRepository.save(team)
        return true
    }

    override fun updateTeam(team: Team): Boolean {
        //check if exist in db
        val tmp = teamRepository.findById(team.id)
        return if(tmp.isPresent) {
            //save new
            teamRepository.save(team)
            true
        }
        else
            false
    }

    override fun deleteTeam(teamId: Long): Boolean {
        val tmp = getTeamById(teamId)
        if (tmp.isPresent) {
            val temporal = tmp.get()
            if(temporal.members.size == 1) {
                val temporalMemberId = temporal.members[0].id
                val adminId = temporal.getAdmin()?.id
                if(adminId == null) {
                    //TODO: ha az admin null, akkor már régen meg kellett volna szűnnie a csoportnak (vagy nem létrejönni)
                    teamRepository.delete(temporal)
                    return true
                } else if(adminId == temporalMemberId) {
                    temporal.removeAdmin()
                    temporal.removeAllMember()
                    teamRepository.delete(temporal)
                    return true
                } else {
                    //TODO: ez itt kutyafasz (az admin nincs benne a csoportban, ha ez itt megtörténik)
                    return false
                }
            }
        }
        return false
    }
}