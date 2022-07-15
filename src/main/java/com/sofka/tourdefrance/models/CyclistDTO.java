package com.sofka.tourdefrance.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CyclistDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotBlank
    @Length(max = 100, message = "Name must be less than 100 characters")
    @Schema(maxLength = 100)
    private String fullName;

    @NotNull
    @Digits(integer = 3, fraction = 0, message = "Competitor number must be less than 3 digits")
    @Schema(format = "int" , maximum = "999", defaultValue = "0")
    private int competitorNumber;

    @NotBlank
    @Length(max = 50, message = "Country must be less than 50 characters")
    @Schema(maxLength = 50)
    private String country;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String cyclingTeamId;

    @NotBlank
    @Length(max = 3, message = "Team code must be less than 3 characters")
    private String cyclingTeamCode;
}
