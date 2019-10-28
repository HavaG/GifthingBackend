package HavaG.Gifthing.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "Gift")
@Table(name = "gift")
class Gift(var name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    var link: String? = null
    var description: String? = null
    var price: Int? = null

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

}