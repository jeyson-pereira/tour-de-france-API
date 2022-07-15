package com.sofka.tourdefrance.models.mapper;

import com.sofka.tourdefrance.collections.CyclingTeam;
import com.sofka.tourdefrance.models.CyclingTeamDTO;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperCyclingTeam {
    //Mapper CyclingTeamDTO to CyclingTeam
    public Function<CyclingTeamDTO, CyclingTeam> toEntity = (CyclingTeamDTO cyclingTeamDTO) -> {
        CyclingTeam cyclingTeam = new CyclingTeam();
        cyclingTeam.setName(cyclingTeamDTO.getName().toUpperCase());
        cyclingTeam.setTeamCode(cyclingTeamDTO.getTeamCode().toUpperCase());
        cyclingTeam.setCountry(WordUtils.capitalizeFully(cyclingTeamDTO.getCountry()));
        return cyclingTeam;
    };

    //Mapper CyclingTeam to CyclingTeamDTO
    public Function<CyclingTeam, CyclingTeamDTO> toDTO = (CyclingTeam cyclingTeam) -> {
        CyclingTeamDTO cyclingTeamDTO = new CyclingTeamDTO();
        cyclingTeamDTO.setId(cyclingTeam.getId());
        cyclingTeamDTO.setName(cyclingTeam.getName());
        cyclingTeamDTO.setTeamCode(cyclingTeam.getTeamCode());
        cyclingTeamDTO.setCountry(cyclingTeam.getCountry());
        return cyclingTeamDTO;
    };

}
