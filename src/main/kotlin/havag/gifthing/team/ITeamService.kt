package havag.gifthing.team

import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse

interface ITeamService {

	fun findAll(): MutableIterable<TeamResponse>
	fun findById(teamId: Long): TeamResponse?
	fun create(team: TeamRequest): TeamResponse
	fun update(team: TeamRequest): TeamResponse?
	fun delete(teamId: Long): Boolean
	fun addUser(groupId: Long, userId: Long): TeamResponse?
}