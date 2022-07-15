package com.sofka.tourdefrance.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CyclingTeamDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotBlank
    @Length(max = 30, message = "Name must be less than 30 characters")
    @Schema(maxLength = 30)
    private String name;

    @NotBlank
    @Length(max = 3, message = "Team code must be less than 3 characters")
    @Schema(maxLength = 3)
    private String teamCode;

    @NotBlank
    @Length(max = 50, message = "Country must be less than 50 characters")
    @Schema(maxLength = 50)
    private String country;
}
