package havag.gifthing.team

import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse
import havag.gifthing.repositories.TeamRepository
import havag.gifthing.repositories.UserRepository
import havag.gifthing.security.services.UserDetailsProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class TeamService(
	val teamRepository: TeamRepository,
	val userRepository: UserRepository,
	val userService: UserDetailsProvider,
	val logger: Logger = LoggerFactory.getLogger(TeamService::class.java)
) : ITeamService {
	//TODO: probaly not working
	override fun addMember(teamId: Long, userId: Long): TeamResponse? {
		val team = teamRepository.findById(teamId)
		val user = userRepository.findById(userId)
		return if (user.isPresent && team.isPresent) {
			val saveTeam = team.get()
			saveTeam.addMember(user.get())
			val result = teamRepository.save(saveTeam)
			logger.info("UserId ${userService.getUser().id} added userId $userId to teamId ${result.id}")
			result.toTeamResponse()
		} else {
			logger.info("Could not add userId $userId to teamId $teamId")
			null
		}
	}

	override fun removeMember(teamId: Long, userId: Long): TeamResponse? {
		TODO("Not yet implemented")
	}

	override fun getMyTeams(): MutableList<TeamResponse> {
		val teams = teamRepository.findAll()
		val result = mutableListOf<TeamResponse>()
		val user = userRepository.findById(userService.getUser().id).get()
		for (i in teams) {
			if (i.isMember(user))
				result.add(i.toTeamResponse())
		}
		logger.info("UserId ${userService.getUser().id} get my teams")
		return result
	}

	override fun findAll(): MutableIterable<TeamResponse> {
		val teams = teamRepository.findAll()
		val result = mutableListOf<TeamResponse>()
		for (i in teams) {
			result.add(i.toTeamResponse())
		}
		logger.info("UserId ${userService.getUser().id} get all teams")
		return result
	}

	override fun findById(teamId: Long): TeamResponse? {
		val tmpTeam = teamRepository.findById(teamId)
		return if (tmpTeam.isPresent) {
			val team = tmpTeam.get()
			var member = false
			val myTeams = userService.getUser().getMyTeams()
			myTeams.forEach {
				if (it.id == team.id)
					member = true
			}
			if (member) {
				logger.info("UserId ${userService.getUser().id} get teamId $teamId")
				team.toTeamResponse()
			} else {
				logger.info("UserId ${userService.getUser().id} not member of the teamId $teamId")
				null
			}
		} else {
			logger.info("Requested team is not exists userId ${userService.getUser().id}")
			null
		}
	}

	override fun create(team: TeamRequest): TeamResponse {
		val saveTeam = team.toTeam(userRepository)
		val result = teamRepository.save(saveTeam)
		logger.info("UserId ${userService.getUser().id} created teamId ${result.id}")
		return result.toTeamResponse()
	}

	override fun update(team: TeamRequest): TeamResponse? {
		val tmp = teamRepository.findById(team.id)
		return if (tmp.isPresent) {
			val user = userService.getUser()
			val myOwnedTeams = user.getMyOwnedTeams()
			var owned = false
			myOwnedTeams.forEach {
				if (it.id == team.id)
					owned = true
			}
			return if (owned) {
				val saveTeam = team.toTeam(userRepository)
				val result = teamRepository.save(saveTeam)
				logger.info("UserId ${userService.getUser().id} updated teamId ${result.id}")
				result.toTeamResponse()
			} else {
				logger.info("Update team failed userId ${user.id} gift ${tmp.get().id} (not owner)")
				null
			}
		} else {
			logger.info("Update team failed userId ${userService.getUser().id} teamId ${team.id}")
			null
		}
	}

	override fun delete(teamId: Long): HttpStatus {
		//TODO: not implemented
		return HttpStatus.NOT_FOUND
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