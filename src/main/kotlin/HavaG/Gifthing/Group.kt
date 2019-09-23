package HavaG.Gifthing

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Group(private var name: String, private var owner: Person) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0
    private var members = listOf<Person>()
}

//TODO: addMember, removeMember, setOwner, setName, getName