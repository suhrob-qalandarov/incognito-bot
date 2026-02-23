package howdy.lab.incognito_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import howdy.lab.incognito_bot.model.SessionState;
import howdy.lab.incognito_bot.model.UserSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final TelegramBot telegramBot;
    private final SessionService sessionService;

    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            Long chatId = message.chat().id();
            String text = message.text();

            log.info("Received message from chat {}: {}", chatId, text);

            UserSession session = sessionService.getOrCreateSession(chatId);

            if (text != null) {
                if (text.startsWith("/start")) {
                    sessionService.updateState(chatId, SessionState.MENU);
                    sendMessage(chatId, "Asosiy menyudasiz. Sizning state: " + SessionState.MENU);
                } else if (text.equals("set")) {
                    sessionService.updateState(chatId, SessionState.WAITING_FOR_MESSAGE);
                    sendMessage(chatId, "State o'zgardi. Sizning state: " + SessionState.WAITING_FOR_MESSAGE);
                } else {
                    sendMessage(chatId, "Joriy state: " + session.getState());
                }
            }
        } else if (update.callbackQuery() != null) {
            log.info("Received callback from {}", update.callbackQuery().from().id());
        } else {
            log.info("Unhandled update type");
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage req = new SendMessage(chatId, text);
        telegramBot.execute(req);
    }
}
