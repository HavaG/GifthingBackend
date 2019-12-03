package HavaG.Gifthing.models.gift.dto

class UserGiftResponse(
        var email: String,
        var password: String,
        var id: Long = 0,
        var name: String? = null
        )