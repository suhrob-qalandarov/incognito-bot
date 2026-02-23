package howdy.lab.incognito_bot.service;

import howdy.lab.incognito_bot.model.SessionState;
import howdy.lab.incognito_bot.model.UserSession;
import howdy.lab.incognito_bot.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserSessionRepository sessionRepository;

    public UserSession getOrCreateSession(Long chatId) {
        return sessionRepository.findById(chatId)
                .orElseGet(() -> sessionRepository.save(
                        UserSession.builder()
                                .chatId(chatId)
                                .state(SessionState.MENU)
                                .build()));
    }

    public void updateState(Long chatId, SessionState newState) {
        UserSession session = getOrCreateSession(chatId);
        session.setState(newState);
        sessionRepository.save(session);
    }

    public void updateTargetUri(Long chatId, String uri) {
        UserSession session = getOrCreateSession(chatId);
        session.setTargetUri(uri);
        sessionRepository.save(session);
    }

    public void clearSession(Long chatId) {
        sessionRepository.deleteById(chatId);
    }

}
