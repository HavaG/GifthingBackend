package HavaG.Gifthing

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Gift(private var name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0
    private var link: String? = null
    private var description: String? = null
    private var price: Int? = null
    private var isReserved: Person? = null
}

//TODO: setName, getName, setLink, getLink, setDescription, getDescription, setPrice, getPrive, setReserved, getReserved