package howdy.lab.incognito_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("UserSession")
public class UserSession implements Serializable {

    @Id
    private Long chatId;

    @Builder.Default
    private SessionState state = SessionState.MENU;

    private String targetUri;

    // Optional: Avtomatik tozalanish vaqti (masalan, 24 soat)
    @TimeToLive(unit = TimeUnit.HOURS)
    @Builder.Default
    private Long timeout = 24L;

}
