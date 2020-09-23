package havag.gifthing.controller.team

import havag.gifthing.models.team.Team
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : CrudRepository<Team, Long>{
    //TODO: TeamRepository
}