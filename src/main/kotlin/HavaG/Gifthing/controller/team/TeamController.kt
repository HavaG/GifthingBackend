package HavaG.Gifthing.controller.team

import HavaG.Gifthing.models.team.Team
import HavaG.Gifthing.models.team.dto.TeamRequest
import HavaG.Gifthing.models.team.dto.TeamResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/team")
class TeamController (val iTeamService: ITeamService){

    @GetMapping("/all")
    fun all(): MutableIterable<TeamResponse> {
        return iTeamService.getAllTeam()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): TeamResponse? {
        return iTeamService.getTeamById(id)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): Boolean{
        return iTeamService.deleteTeam(id)
    }

    @PutMapping("/update")
    fun update(@RequestBody editTeam: TeamRequest): TeamResponse? {
        return iTeamService.updateTeam(editTeam)
    }

    @PostMapping("/createUser")
    fun create(@RequestBody newTeam: TeamRequest): TeamResponse {
        return iTeamService.createTeam(newTeam)
    }
}