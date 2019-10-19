package HavaG.Gifthing.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Group(var name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    //var owner: User
    //var members = listOf<User>()
}

//TODO: addMember, removeMember, setOwner, setName, getName