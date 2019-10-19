package HavaG.Gifthing.models

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private var owner: User? = null

    fun setOwner(user: User?) {
        owner = user
    }

    @ManyToOne
    @JoinColumn(name = "reserve_id")
    private var reservedBy: User? = null


    fun setReserveBy(user: User){
        reservedBy = user
    }

}

//TODO: setName, getName, setLink, getLink, setDescription, getDescription, setPrice, getPrive, setReserved, getReserved