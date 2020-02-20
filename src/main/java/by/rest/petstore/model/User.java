package by.rest.petstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull
    @PositiveOrZero
    private long id;

    @NotBlank
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    @NotBlank
    private String password;
    private String phone;
    private int userStatus;
}
