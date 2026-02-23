package howdy.lab.incognito_bot.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookRegistration {

    private final TelegramBot telegramBot;

    @Value("${telegram.bot.webhook.url}")
    private String webhookUrl;

    @Value("${telegram.bot.webhook.secret}")
    private String webhookSecret;

    @PostConstruct
    public void registerWebhook() {
        log.info("Setting webhook to URL: {}", webhookUrl);
        SetWebhook request = new SetWebhook().url(webhookUrl).secretToken(webhookSecret);
        BaseResponse response = telegramBot.execute(request);

        if (response != null && response.isOk()) {
            log.info("Webhook successfully set to {}", webhookUrl);
        } else {
            log.error("Failed to set webhook. Error: {}", response != null ? response.description() : "null response");
        }
    }
}
