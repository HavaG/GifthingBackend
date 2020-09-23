package havag.gifthing.controller.team

import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse

interface ITeamService {

    fun getAllTeam(): MutableIterable<TeamResponse>
    fun getTeamById(teamId: Long): TeamResponse?
    fun createTeam(team: TeamRequest): TeamResponse
    fun updateTeam(team: TeamRequest): TeamResponse?
    fun deleteTeam(teamId: Long): Boolean
    fun addUser(groupId: Long, userId: Long): TeamResponse?
}