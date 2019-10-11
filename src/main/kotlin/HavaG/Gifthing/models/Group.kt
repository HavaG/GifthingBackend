package HavaG.Gifthing.models

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Group(private var name: String, private var owner: User) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0
    private var members = listOf<User>()
}

//TODO: addMember, removeMember, setOwner, setName, getName