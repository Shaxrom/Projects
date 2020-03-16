package java.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.models.Token;

@Data
@AllArgsConstructor
public class TokenDto {
    private String value;

    public static TokenDto from(Token token) {
        return new TokenDto(token.getValue());
    }

}
