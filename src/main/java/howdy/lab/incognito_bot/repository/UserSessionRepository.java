package howdy.lab.incognito_bot.repository;

import howdy.lab.incognito_bot.model.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {
}
