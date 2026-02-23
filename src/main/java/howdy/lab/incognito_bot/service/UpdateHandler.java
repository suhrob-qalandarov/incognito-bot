package howdy.lab.incognito_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final TelegramBot telegramBot;

    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            Long chatId = message.chat().id();
            String text = message.text();

            log.info("Received message from chat {}: {}", chatId, text);

            if (text != null) {
                sendMessage(chatId, "test");
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
