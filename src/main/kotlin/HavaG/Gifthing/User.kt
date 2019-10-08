package HavaG.Gifthing

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class User(var name: String, var password: String) {
    constructor() : this("", "")


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0


}
    /*
    private var groupList = mutableListOf<Group>()
    private var giftList = mutableListOf<Gift>()

    fun addGift(gift: Gift) {
        giftList.add(gift)
    }

    fun deleteGift(gift: Gift) {
        giftList.remove(gift)
    }

    fun joinGroup(group: Group) {
        groupList.add(group)
    }

    fun leaveGroup(group: Group) {
        groupList.remove(group)
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setPwd(pwd: String) {
        this.pwd = pwd
    }
    */

