package com.sofka.tourdefrance.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection= "cyclists")
public class Cyclist {
    @Id
    private String id;
    private String fullName;
    @Indexed(unique = true) // unique = true means the competitorNumber must be unique in the collection.
    private int competitorNumber;
    private String country;
    private String cyclingTeamId;
    private String cyclingTeamCode;
}
