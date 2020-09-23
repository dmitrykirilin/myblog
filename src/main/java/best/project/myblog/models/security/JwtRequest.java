package best.project.myblog.models.security;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @Size(min = 4, max = 20, message = "Придумайте никнейм от 4 до 20 символов!")
    private String username;

    @Size(min = 4, message = "Пароль должен быть от 4 до 10 символов!")
    private String password;

}
