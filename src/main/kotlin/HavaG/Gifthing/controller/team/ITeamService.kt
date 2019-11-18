package HavaG.Gifthing.controller.team

import HavaG.Gifthing.models.team.Team
import java.util.*

interface ITeamService {

    fun getAllTeam(): MutableIterable<Team>
    fun getTeamById(teamId: Long): Optional<Team>
    fun addTeam(team: Team): Boolean
    fun updateTeam(team: Team): Boolean
    fun deleteTeam(teamId: Long): Boolean
}