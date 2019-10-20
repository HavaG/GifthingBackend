package HavaG.Gifthing.Controller.Group

import HavaG.Gifthing.Models.Group
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/Group")
class GroupController (val iGroupService: IGroupService){

    @GetMapping("/all")
    fun all(): MutableIterable<Group> {
        return iGroupService.getAllGroup()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): Optional<Group> {
        return iGroupService.getGroupById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long){
        iGroupService.deleteGroup(id)
    }

    @PutMapping("/modify")
    fun update(@RequestBody editGroup: Group): Boolean {
        return iGroupService.updateGroup(editGroup)
    }

    @PostMapping("/create")
    fun create(@RequestBody newGroup: Group): Boolean {
        return iGroupService.addGroup(newGroup)
    }
}