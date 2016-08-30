package cvut.fit.domain.repository;

import cvut.fit.domain.entity.RedditEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Repository
public interface RedditEntryRepository extends CrudRepository<RedditEntry, Integer> {
    Iterable<RedditEntry> findByAuthor(String sirBelvedere);

}
