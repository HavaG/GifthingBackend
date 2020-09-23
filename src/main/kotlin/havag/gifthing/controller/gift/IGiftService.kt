package havag.gifthing.controller.gift

import havag.gifthing.models.gift.dto.GiftRequest
import havag.gifthing.models.gift.dto.GiftResponse

interface IGiftService {
    fun getAllGift(): MutableIterable<GiftResponse>
    fun getGiftById(giftId: Long): GiftResponse?
    fun createGift(gift: GiftRequest): GiftResponse
    fun updateGift(gift: GiftRequest): GiftResponse?
    fun deleteGift(giftId: Long): Boolean
    fun reserveGift(giftId: Long, userId: Long): GiftResponse?
}