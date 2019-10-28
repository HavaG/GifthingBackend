package HavaG.Gifthing.Models

import javax.persistence.*

@Entity(name = "Group")
@Table(name = "group")
class Group(var name: String)
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    //TODO: group many-to-one to user (get admin in the constructor)

    /*
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private var admin: User? = null

    fun setAdmin(user: User) {
        admin = user
    }
*/

/*    TODO: Group many-to-many members
    @ManyToMany(mappedBy = "myGroups")
    var members = listOf<User>()*/
}

//TODO: Group: addMember, removeMember, setOwner, setName, getName