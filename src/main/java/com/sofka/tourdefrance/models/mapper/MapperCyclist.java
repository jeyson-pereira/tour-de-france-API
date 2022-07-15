package com.sofka.tourdefrance.models.mapper;

import com.sofka.tourdefrance.collections.Cyclist;
import com.sofka.tourdefrance.models.CyclistDTO;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperCyclist {
    //Mapper CyclistDTO to Cyclist
    public Function<CyclistDTO, Cyclist> toEntity = (CyclistDTO cyclistDTO) -> {
        Cyclist cyclist = new Cyclist();
        cyclist.setFullName(WordUtils.capitalizeFully(cyclistDTO.getFullName()));
        cyclist.setCompetitorNumber(cyclistDTO.getCompetitorNumber());
        cyclist.setCountry(WordUtils.capitalizeFully(cyclistDTO.getCountry()));
        cyclist.setCyclingTeamId(cyclistDTO.getCyclingTeamId());
        cyclist.setCyclingTeamCode(cyclistDTO.getCyclingTeamCode().toUpperCase());
        return cyclist;
    };

    //Mapper Cyclist to CyclistDTO
    public Function<Cyclist, CyclistDTO> toDTO = (Cyclist cyclist) -> {
        CyclistDTO cyclistDTO = new CyclistDTO();
        cyclistDTO.setId(cyclist.getId());
        cyclistDTO.setFullName(cyclist.getFullName());
        cyclistDTO.setCompetitorNumber(cyclist.getCompetitorNumber());
        cyclistDTO.setCountry(cyclist.getCountry());
        cyclistDTO.setCyclingTeamId(cyclist.getCyclingTeamId());
        cyclistDTO.setCyclingTeamCode(cyclist.getCyclingTeamCode());
        return cyclistDTO;
    };

}
