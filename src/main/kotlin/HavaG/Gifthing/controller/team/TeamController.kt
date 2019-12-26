package HavaG.Gifthing.controller.team

import HavaG.Gifthing.models.team.Team
import HavaG.Gifthing.models.team.dto.TeamRequest
import HavaG.Gifthing.models.team.dto.TeamResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/team")
class TeamController (val iTeamService: ITeamService){

    @GetMapping("/all")
    fun all(): ResponseEntity<MutableIterable<TeamResponse>> {
        return ResponseEntity(iTeamService.getAllTeam(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<TeamResponse> {
        val tmp =  iTeamService.getTeamById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean>{
        return if(iTeamService.deleteTeam(id)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/update")
    fun update(@RequestBody editTeam: TeamRequest): ResponseEntity<TeamResponse> {
        val tmp = iTeamService.updateTeam(editTeam)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/create")
    fun create(@RequestBody newTeam: TeamRequest): ResponseEntity<TeamResponse> {
        return ResponseEntity(iTeamService.createTeam(newTeam), HttpStatus.OK)
    }

    @PutMapping("/add/{groupId}/{userId}")
    fun addUser(@PathVariable(value = "groupId") groupId: Long,
                @PathVariable(value = "userId") userId: Long): ResponseEntity<TeamResponse> {
        val tmp = iTeamService.addUser(groupId, userId)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }
}