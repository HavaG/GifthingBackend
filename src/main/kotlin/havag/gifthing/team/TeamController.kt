package havag.gifthing.team

import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/team")
class TeamController(val iTeamService: ITeamService) {

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	fun findAll(): ResponseEntity<MutableIterable<TeamResponse>> {
		return ResponseEntity(iTeamService.findAll(), HttpStatus.OK)
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<TeamResponse> {
		val team = iTeamService.findById(id)
		return team?.let { ResponseEntity(team, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@GetMapping("/my-teams")
	@PreAuthorize("hasRole('USER')")
	fun getMyTeams(): ResponseEntity<MutableList<TeamResponse>> {
		return ResponseEntity(iTeamService.getMyTeams(), HttpStatus.OK)
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('MODERATOR')")
	fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean> {
		val status = iTeamService.delete(id)
		val success = status.is2xxSuccessful
		return ResponseEntity(success, status)
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('MODERATOR')")
	fun update(@RequestBody editTeam: TeamRequest): ResponseEntity<TeamResponse> {
		val team = iTeamService.update(editTeam)
		return team?.let { ResponseEntity(team, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	fun create(@RequestBody newTeam: TeamRequest): ResponseEntity<TeamResponse> {
		return ResponseEntity(iTeamService.create(newTeam), HttpStatus.OK)
	}

	//TODO: invite link pls (not working btw)
	@PutMapping("/join/{teamId}/{userId}")
	@PreAuthorize("hasRole('USER')")
	fun join(
		@PathVariable(value = "teamId") teamId: Long,
		@PathVariable(value = "userId") userId: Long
	): ResponseEntity<TeamResponse> {
		val team = iTeamService.addMember(teamId, userId)
		return team?.let { ResponseEntity(team, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	//TODO: not implemented
	@PutMapping("/remove/{teamId}/{userId}")
	@PreAuthorize("hasRole('MODERATOR')")
	fun remove(
		@PathVariable(value = "teamId") teamId: Long,
		@PathVariable(value = "userId") userId: Long
	): ResponseEntity<TeamResponse> {
		val team = iTeamService.removeMember(teamId, userId)
		return team?.let { ResponseEntity(team, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}
}