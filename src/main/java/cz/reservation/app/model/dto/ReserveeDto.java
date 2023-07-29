package cz.reservation.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReserveeDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
