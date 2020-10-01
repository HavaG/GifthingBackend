package havag.gifthing.models.team

import havag.gifthing.models.team.dto.TeamResponse
import havag.gifthing.models.user.User
import havag.gifthing.models.user.dto.TeamUserResponse
import javax.persistence.*

@Entity(name = "Team")
@Table(name = "teams")
class Team(var name: String)
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0

    @ManyToOne(cascade= [CascadeType.ALL])
    @JoinColumn(name = "admin_id")
    private var admin: User? = null

    fun setAdmin(user: User) {
        admin = user
        user.addMyOwnedTeam(this)
        addMember(user)
    }

    fun removeAdmin() {
        admin = null
    }

    fun getAdmin(): User? {
        return admin
    }

    @ManyToMany(mappedBy = "myTeams",
                cascade= [CascadeType.ALL])
    var members = mutableListOf<User>()

    fun addMember(user: User) {
        //csak akkor adja hozzá, ha nincs még benne
        if(!members.contains(user)){
            members.add(user)
            user.addMyTeam(this)
        }
    }

    fun removeMember(user: User) {
        members.remove(user)
    }
    fun removeAllMember() {
        for(i in members) {
            i.removeMyTeam(this)
        }
        members = mutableListOf<User>()
    }

    fun toTeamUserResponse(): TeamUserResponse {
        return TeamUserResponse(this.name, this.id)
    }

    fun toTeamResponse(): TeamResponse {
        val result =  TeamResponse(this.name, this.id, this.admin!!.id)
        result.setTeamMembers(this.members)
        return result
    }

    fun isMember(user: User): Boolean {
        return members.contains(user)
    }
}