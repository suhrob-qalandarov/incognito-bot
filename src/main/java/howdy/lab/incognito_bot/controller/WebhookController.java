package howdy.lab.incognito_bot.controller;

import com.pengrad.telegrambot.utility.BotUtils;
import com.pengrad.telegrambot.model.Update;
import howdy.lab.incognito_bot.service.UpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final UpdateHandler updateHandler;

    @Value("${telegram.bot.webhook.secret}")
    private String expectedSecretToken;

    @PostMapping
    public ResponseEntity<String> receiveUpdate(
            @RequestHeader(value = "X-Telegram-Bot-Api-Secret-Token", required = false) String secretToken,
            @RequestBody String requestBody) {

        // Vertification of Secret Token
        if (expectedSecretToken != null && !expectedSecretToken.equals(secretToken)) {
            log.warn("Invalid or missing secret token in webhook request: {}", secretToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Update update = BotUtils.parseUpdate(requestBody);
            updateHandler.handleUpdate(update);
        } catch (Exception e) {
            log.error("Failed to process webhook update", e);
            // It is often better to return 200 OK so that Telegram doesn't retry on our
            // internal errors constantly.
            // But if it's a parsing issue, 400 is fine.
        }

        return ResponseEntity.ok().build();
    }
}
