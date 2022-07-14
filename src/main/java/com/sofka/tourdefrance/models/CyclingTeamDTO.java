package com.sofka.tourdefrance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CyclingTeamDTO {
    private String id;

    @NotBlank
    @Max(value = 30, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank
    @Max(value = 3, message = "Team code must be less than 3 characters")
    private String teamCode;

    @NotBlank
    @Max(value = 50, message = "Country must be less than 50 characters")
    private String country;
}
