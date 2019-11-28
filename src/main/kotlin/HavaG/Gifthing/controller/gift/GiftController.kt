package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.gift.Gift
import HavaG.Gifthing.models.gift.dto.GiftResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@RestController
@RequestMapping("/gift")
class GiftController (val iGiftService: IGiftService){

    @GetMapping("/all")
    fun all(): MutableIterable<GiftResponse> {
        return iGiftService.getAllGift()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): GiftResponse? {
        return iGiftService.getGiftById(id)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): Boolean{
        return iGiftService.deleteGift(id)
    }

    @PutMapping("/update")
    fun update(@RequestBody editGift: Gift): Boolean {
        return iGiftService.updateGift(editGift)
    }

    @PostMapping("/create")
    fun create(@RequestBody newGift: Gift): Gift     {
        return iGiftService.saveGift(newGift)
    }
}