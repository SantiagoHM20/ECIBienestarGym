package edu.eci.cvds.ECIBienestarGym.model;

import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "routines")
public class Routine {
    @Id
    private String id;
    private String name;
    private String description;
    @Field("exercises")
    private List<Exercise> exercises;
    private int durationDays;
    private DifficultyLevel difficulty;
}
