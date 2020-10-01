package havag.gifthing.team

import havag.gifthing.models.team.dto.TeamRequest
import havag.gifthing.models.team.dto.TeamResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/team")
class TeamController (val iTeamService: ITeamService){

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun all(): ResponseEntity<MutableIterable<TeamResponse>> {
        return ResponseEntity(iTeamService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<TeamResponse> {
        val tmp =  iTeamService.findById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean>{
        return if(iTeamService.delete(id)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR')")
    fun update(@RequestBody editTeam: TeamRequest): ResponseEntity<TeamResponse> {
        val tmp = iTeamService.update(editTeam)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    fun create(@RequestBody newTeam: TeamRequest): ResponseEntity<TeamResponse> {
        return ResponseEntity(iTeamService.create(newTeam), HttpStatus.OK)
    }

    //TODO: invite link pls (not working btw)
    @PutMapping("/join/{teamId}/{userId}")
    @PreAuthorize("hasRole('USER')")
    fun join(@PathVariable(value = "teamId") teamId: Long,
             @PathVariable(value = "userId") userId: Long): ResponseEntity<TeamResponse> {
        val tmp = iTeamService.addMember(teamId, userId)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    //TODO: not implemented
    @PutMapping("/remove/{teamId}/{userId}")
    @PreAuthorize("hasRole('MODERATOR')")
    fun remove(@PathVariable(value = "teamId") teamId: Long,
               @PathVariable(value = "userId") userId: Long): ResponseEntity<TeamResponse> {
        val tmp = iTeamService.removeMember(teamId, userId)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }
}