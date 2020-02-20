package by.rest.petstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {
    private String token;
    private User user;
    private Pet pet;
    private Order order;
    private String petName;
    private Pet.Status petStatus;
    private String petPhotoUrl;
}
