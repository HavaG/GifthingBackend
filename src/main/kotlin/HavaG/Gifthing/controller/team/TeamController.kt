package HavaG.Gifthing.controller.team

import HavaG.Gifthing.models.team.Team
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/team")
class TeamController (val iTeamService: ITeamService){

    @GetMapping("/all")
    fun all(): MutableIterable<Team> {
        return iTeamService.getAllTeam()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): Optional<Team> {
        return iTeamService.getTeamById(id)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): Boolean{
        return iTeamService.deleteTeam(id)
    }

    @PutMapping("/update")
    fun update(@RequestBody editTeam: Team): Boolean {
        return iTeamService.updateTeam(editTeam)
    }

    @PostMapping("/create")
    fun create(@RequestBody newTeam: Team): Boolean {
        return iTeamService.addTeam(newTeam)
    }
}