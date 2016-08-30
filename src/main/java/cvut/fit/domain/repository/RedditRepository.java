package cvut.fit.domain.repository;

import cvut.fit.domain.entity.RedditEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Repository
public interface RedditRepository extends CrudRepository<RedditRepository, Integer> {

    List<RedditEntry> findByAuthor(String sirBelvedere);
}
