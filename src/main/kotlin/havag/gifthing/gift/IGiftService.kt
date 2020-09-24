package havag.gifthing.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse
import havag.gifthing.models.user.dto.GiftUserResponse

interface IGiftService {
	fun findAll(): MutableIterable<GiftResponse>
	fun findById(giftId: Long): GiftResponse?
	fun create(gift: GiftRequest): GiftResponse
	fun update(gift: GiftRequest): GiftResponse?
	fun delete(giftId: Long): Boolean
	fun reserve(giftId: Long): GiftResponse?
	fun myGifts(): MutableIterable<GiftUserResponse>
}