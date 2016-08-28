package cvut.fit.domain.repository;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Repository
public interface BlogUpdateEntryRepository extends CrudRepository<BlogUpdateEntry, Integer> {
    List<BlogUpdateEntry> findByValveId(int valveId);

}



