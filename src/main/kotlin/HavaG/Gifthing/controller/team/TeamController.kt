package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Team
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/Team")
class GroupController (val iGroupService: IGroupService){

    @GetMapping("/all")
    fun all(): MutableIterable<Team> {
        return iGroupService.getAllGroup()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): Optional<Team> {
        return iGroupService.getGroupById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long){
        iGroupService.deleteGroup(id)
    }

    @PutMapping("/modify")
    fun update(@RequestBody editTeam: Team): Boolean {
        return iGroupService.updateGroup(editTeam)
    }

    @PostMapping("/create")
    fun create(@RequestBody newTeam: Team): Boolean {
        return iGroupService.addGroup(newTeam)
    }
}