package havag.gifthing.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.GiftUserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/api/gift")
class GiftController (val iGiftService: IGiftService){

    //TODO: Ennek van értelme?
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun all(): ResponseEntity<MutableIterable<GiftResponse>> {
        return ResponseEntity(iGiftService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/my-gifts")
    @PreAuthorize("hasRole('USER')")
    fun myGifts(): ResponseEntity<MutableIterable<GiftUserResponse>> {
        return ResponseEntity(iGiftService.myGifts(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    fun findById(@PathVariable(value = "id") id: Long): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.findById(id)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean>{
        return ResponseEntity(iGiftService.delete(id), HttpStatus.OK)
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    fun update(@RequestBody editGift: GiftRequest): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.update(editGift)
        return if(tmp != null) {
            ResponseEntity(tmp, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    fun create(@RequestBody newGift: GiftRequest): ResponseEntity<GiftResponse>     {
        return ResponseEntity(iGiftService.create(newGift), HttpStatus.OK)
    }

    @PutMapping("/reserve/{giftId}")
    @PreAuthorize("hasRole('USER')")
    fun reserveGift(@PathVariable(value = "giftId") giftId: Long): ResponseEntity<GiftResponse> {
        val tmp = iGiftService.reserve(giftId)
        return if(tmp != null)
            ResponseEntity(tmp, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.CONFLICT)
    }
}