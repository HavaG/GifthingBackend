package HavaG.Gifthing.Models

import javax.persistence.*


@Entity(name = "User")
@Table(name = "user")
class User(var email: String, var password: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    var name: String? = null

    @OneToMany(mappedBy = "owner")
    private var gifts = mutableListOf<Gift>()

    fun addGift(gift: Gift) {
        gifts.add(gift)
        gift.setOwner(this)
    }

    fun removeGift(gift: Gift) {
        gifts.remove(gift)
        gift.setOwner(null)
    }

    fun getGifts(): MutableList<Gift> {
        return gifts
    }

    fun setGifts(gifts: MutableList<Gift>) {
        this.gifts = gifts
    }


    @OneToMany(mappedBy = "reservedBy")
    private var reservedGifts = mutableListOf<Gift>()

    fun reserveGift(gift: Gift) {
        reservedGifts.add(gift)
        gift.setReserveBy(this)
    }

    fun removeReservedGift(gift: Gift) {
        reservedGifts.remove(gift)
    }


    fun getReservedGifts(): MutableList<Gift> {
        return reservedGifts
    }

    fun setReservedGifts(reservedGifts: MutableList<Gift>) {
        this.reservedGifts = reservedGifts
    }

    @OneToMany(mappedBy = "admin")
    private var myOwnedGroup = mutableListOf<Group>()

    fun setMyGroup(group: Group) {
        myOwnedGroup.add(group)
    }

/*    TODO: User: @ManyToMany myGroups
    @JoinTable(
            name = "users_groups",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "group_id")])
    private var myGroups = mutableListOf<Group>()*/
}