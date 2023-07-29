package cz.reservation.app.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
@SuperBuilder
public class Reservee {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}