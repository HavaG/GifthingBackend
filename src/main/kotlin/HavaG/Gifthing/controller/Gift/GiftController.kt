package HavaG.Gifthing.controller.Gift

import HavaG.Gifthing.models.Gift
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/gift")
class GiftController (iGiftService: IGiftService){

    val iGiftService = iGiftService

    //probably useless
    @GetMapping("/all")
    fun all(): MutableIterable<Gift> {
        return iGiftService.getAllGift()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): Optional<Gift> {
        return iGiftService.getGiftById(id)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long){
        iGiftService.deleteGift(id)
    }

    @PutMapping("/modify")
    fun update(@RequestBody editGift: Gift): Boolean {
        return iGiftService.updateGift(editGift)
    }

    @PostMapping("/create")
    fun create(@RequestBody newGift: Gift): Boolean {
        return iGiftService.addGift(newGift)
    }
}