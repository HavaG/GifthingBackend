package HavaG.Gifthing.controller.team

import HavaG.Gifthing.models.team.Team
import HavaG.Gifthing.models.team.dto.TeamRequest
import HavaG.Gifthing.models.team.dto.TeamResponse
import java.util.*

interface ITeamService {

    fun getAllTeam(): MutableIterable<TeamResponse>
    fun getTeamById(teamId: Long): TeamResponse?
    fun createTeam(team: TeamRequest): TeamResponse
    fun updateTeam(team: TeamRequest): TeamResponse?
    fun deleteTeam(teamId: Long): Boolean
    fun addUser(groupId: Long, userId: Long): TeamResponse?
}