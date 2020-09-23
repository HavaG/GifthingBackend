package HavaG.Gifthing.controller.gift

import HavaG.Gifthing.models.gift.dto.GiftRequest
import HavaG.Gifthing.models.gift.dto.GiftResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/gift")
class GiftController (val iGiftService: IGiftService){

    @GetMapping("/all")
    fun all(): ResponseEntity<MutableIterable<GiftResponse>> {
        return ResponseEntity(iGiftService.getAllGift(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.getGiftById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean>{
        return ResponseEntity(iGiftService.deleteGift(id), HttpStatus.OK)
    }

    @PutMapping("/update")
    fun update(@RequestBody editGift: GiftRequest): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.updateGift(editGift)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/create")
    fun create(@RequestBody newGift: GiftRequest): ResponseEntity<GiftResponse>     {
        return ResponseEntity(iGiftService.createGift(newGift), HttpStatus.OK)
    }

    @PutMapping("/reserve/{giftId}/{userId}")
    fun reserveGift(@PathVariable(value = "giftId") giftId: Long,
                    @PathVariable(value = "userId") userId: Long): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.reserveGift(giftId, userId)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.CONFLICT)
    }
}