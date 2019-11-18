package HavaG.Gifthing.models.gift.dto

import HavaG.Gifthing.models.gift.Gift

class UserGiftResponse(
        var email: String,
        var password: String,
        var id: Long = 0,
        var name: String? = null
        )