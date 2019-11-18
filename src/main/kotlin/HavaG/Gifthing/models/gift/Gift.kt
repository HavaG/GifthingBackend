package HavaG.Gifthing.models.gift

import HavaG.Gifthing.models.gift.dto.GiftResponse
import HavaG.Gifthing.models.gift.dto.UserGiftResponse
import HavaG.Gifthing.models.user.dto.GiftUserResponse
import HavaG.Gifthing.models.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity(name = "Gift")
@Table(name = "gift")
class Gift(var name: String,
           var link: String? = null,
           var description: String? = null,
           var price: Int? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @JsonIgnore
    @ManyToOne(cascade= [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    private var owner: User? = null

    fun setOwner(user: User?) {
        owner = user
    }

    @JsonIgnore
    fun getOwner(): User? {
        return owner
    }

    fun removeOwner() {
        setOwner(null)
        setReserveBy(null)
    }

    @ManyToOne(cascade= [CascadeType.ALL])
    @JoinColumn(name = "reserve_id")
    private var reservedBy: User? = null

    fun setReserveBy(user: User?){
        reservedBy = user
    }

    @JsonIgnore
    fun getReserveBy(): User? {
        return reservedBy
    }

    fun removeReserveBy() {
        setReserveBy(null)
    }

    fun toGiftUserResponse(): GiftUserResponse {
        return GiftUserResponse(
                this.name,
                this.id,
                this.description,
                this.link,
                this.price)
    }

    fun toGiftResponse(): GiftResponse {
        val tmp = GiftResponse(
                this.id,
                this.name,
                this.link,
                this.description,
                this.price
                )
        if(this.owner != null) {
            tmp.owner = this.getOwner()!!.userToUserGiftResponse()
        }
        if(this.reservedBy != null) {
            tmp.reservedBy = this.getReserveBy()!!.userToUserGiftResponse()
        }

        return tmp
    }
}