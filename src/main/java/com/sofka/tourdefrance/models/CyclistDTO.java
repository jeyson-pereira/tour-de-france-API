package com.sofka.tourdefrance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CyclistDTO {
    private String id;

    @NotBlank
    @Max(value = 100, message = "Name must be less than 50 characters")
    private String fullName;

    @NotBlank
    @Max(value = 3, message = "Competitor number must be less than 3 digits")
    private int competitorNumber;

    @NotBlank
    @Max(value = 50, message = "Country must be less than 50 characters")
    private String country;

    @NotBlank
    private String cyclingTeamId;

    @NotBlank
    @Max(value = 3, message = "Team code must be less than 3 characters")
    private String cyclingTeamCode;
}
