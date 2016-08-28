package cvut.fit.domain.repository;

import cvut.fit.domain.entity.BlogEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Repository
public interface BlogUpdateEntryRepository extends CrudRepository<BlogEntry, Integer> {


}



