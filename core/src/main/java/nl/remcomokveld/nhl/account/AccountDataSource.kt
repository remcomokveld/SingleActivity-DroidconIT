package nl.remcomokveld.nhl.account

import io.reactivex.Maybe
import nl.remcomokveld.nhl.models.NHLAccount

interface AccountDataSource {
    fun accountOnce(): Maybe<NHLAccount>

}
