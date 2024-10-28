package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.mappers.ExerciseMapper;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.models.ExerciseType;
import glushkova.kristina.gym_tracker.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    public UUID createExercise(String name, ExerciseType type) {
        return exerciseRepository.save(exerciseMapper.map(new ExerciseModel(null, name, type))).getId();
    }

    public List<ExerciseModel> getExercisesByIds(List<UUID> exerciseIds) {
        return exerciseRepository.findAllById(exerciseIds)
                .stream()
                .map(exerciseMapper::map)
                .toList();
    }

    public List<ExerciseModel> getAllPossibleExercises() {
        return exerciseRepository.findAll().stream()
                .map(exerciseMapper::map)
                .toList();
    }
}
