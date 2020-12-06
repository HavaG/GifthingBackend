package havag.gifthing.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface IGiftService {
	fun findById(giftId: Long): GiftResponse?
	fun create(gift: GiftRequest): GiftResponse
	fun delete(giftId: Long): HttpStatus
	fun release(giftId: Long): GiftResponse?
	fun reserve(giftId: Long): ResponseEntity<GiftResponse>
	/*
	fun update(gift: GiftRequest): GiftResponse?
	fun findAll(): MutableIterable<GiftResponse>
	fun myGifts(): MutableIterable<GiftResponse>
	fun hisGifts(userId: Long): MutableIterable<GiftResponse>
	 */
}