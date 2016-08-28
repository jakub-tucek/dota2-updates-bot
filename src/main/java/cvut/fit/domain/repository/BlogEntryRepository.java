package cvut.fit.domain.repository;

import cvut.fit.domain.entity.BlogEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Repository
public interface BlogEntryRepository extends CrudRepository<BlogEntry, Integer> {
    List<BlogEntry> findByValveId(int valveId);

}



