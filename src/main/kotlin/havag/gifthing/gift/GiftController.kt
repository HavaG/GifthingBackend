package havag.gifthing.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.GiftUserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/gift")
class GiftController(val iGiftService: IGiftService) {

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
		val gift = iGiftService.findById(id)
		return gift?.let { ResponseEntity(it, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	fun deleteById(@PathVariable(value = "id") id: Long): ResponseEntity<Boolean> {
		val status = iGiftService.delete(id)
		val success = status.is2xxSuccessful
		return ResponseEntity(success, status)
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('USER')")
	fun update(@RequestBody editGift: GiftRequest): ResponseEntity<GiftResponse> {
		val gift = iGiftService.update(editGift)
		return gift?.let { ResponseEntity(gift, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	fun create(@RequestBody newGift: GiftRequest): ResponseEntity<GiftResponse> {
		return ResponseEntity(iGiftService.create(newGift), HttpStatus.OK)
	}

	@PutMapping("/reserve/{giftId}")
	@PreAuthorize("hasRole('USER')")
	fun reserveGift(@PathVariable(value = "giftId") giftId: Long): ResponseEntity<GiftResponse> {
		val gift = iGiftService.reserve(giftId)
		return gift?.let { ResponseEntity(gift, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.CONFLICT)
	}
}