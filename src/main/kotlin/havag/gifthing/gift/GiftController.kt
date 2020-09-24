package havag.gifthing.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.GiftUserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/api/gift")
class GiftController (val iGiftService: IGiftService){

    @GetMapping("/all")
    fun all(): ResponseEntity<MutableIterable<GiftResponse>> {
        return ResponseEntity(iGiftService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/my-gifts")
    fun myGifts(): ResponseEntity<MutableIterable<GiftUserResponse>> {
        return ResponseEntity(iGiftService.myGifts(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.findById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean>{
        return ResponseEntity(iGiftService.delete(id), HttpStatus.OK)
    }

    @PutMapping("/update")
    fun update(@RequestBody editGift: GiftRequest): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.update(editGift)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/create")
    fun create(@RequestBody newGift: GiftRequest): ResponseEntity<GiftResponse>     {
        return ResponseEntity(iGiftService.create(newGift), HttpStatus.OK)
    }

    @PutMapping("/reserve/{giftId}/{userId}")
    fun reserveGift(@PathVariable(value = "giftId") giftId: Long,
                    @PathVariable(value = "userId") userId: Long): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.reserve(giftId, userId)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.CONFLICT)
    }
}