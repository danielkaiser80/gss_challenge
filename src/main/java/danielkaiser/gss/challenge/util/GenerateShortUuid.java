package danielkaiser.gss.challenge.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenerateShortUuid {

    public String generate(int length) {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, length);
    }
}
