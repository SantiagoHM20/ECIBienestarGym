package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ExerciseDTO;
import edu.eci.cvds.ECIBienestarGym.dto.PhysicalProgressDTO;
import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.ExerciseType;
import edu.eci.cvds.ECIBienestarGym.enums.MuscleGroup;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.PhysicalProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PhysicalProgressServiceTest {

    @Mock
    private PhysicalProgressRepository physicalProgressRepository;

    @InjectMocks
    private PhysicalProgressService physicalProgressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ShouldGetAllPhysicalProgress() {
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressRepository.findAll()).thenReturn(mockProgress);

        List<PhysicalProgress> progressList = physicalProgressService.getAllPhysicalProgress();

        assertEquals(2, progressList.size());
        verify(physicalProgressRepository, times(1)).findAll();
    }

    @Test
    void ShouldGetPhysicalProgressById() throws GYMException {
        String id = "progress123";
        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressRepository.findById(id)).thenReturn(Optional.of(mockProgress));

        PhysicalProgress progress = physicalProgressService.getPhysicalProgressById(id);

        assertEquals(mockProgress, progress);
        verify(physicalProgressRepository, times(1)).findById(id);
    }

    @Test
    void ShouldThrowExceptionWhenPhysicalProgressNotFoundById() {
        String id = "nonexistent-id";
        when(physicalProgressRepository.findById(id)).thenReturn(Optional.empty());

        GYMException thrown = assertThrows(
                GYMException.class,
                () -> physicalProgressService.getPhysicalProgressById(id)
        );

        assertEquals(GYMException.PHYSICAL_PROGRESS_NOT_FOUND, thrown.getMessage());
        verify(physicalProgressRepository, times(1)).findById(id);
    }

    @Test
    void ShouldGetPhysicalProgressByDate() {
        LocalDate date = LocalDate.now();
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressRepository.findByRegistrationDate(date)).thenReturn(mockProgress);

        List<PhysicalProgress> progressList = physicalProgressService.getPhysicalProgressByRegistrationDate(date);

        assertEquals(2, progressList.size());
        verify(physicalProgressRepository, times(1)).findByRegistrationDate(date);
    }




    @Test
    void ShouldCreatePhysicalProgress() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("user123");
        userDTO.setName("John Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setRole(Role.STUDENT);

        RoutineDTO routineDTO = new RoutineDTO();
        routineDTO.setId("routine123");
        routineDTO.setName("Routine A");
        routineDTO.setDescription("Routine A description");
        routineDTO.setExercises(Arrays.asList(
            new ExerciseDTO("Exercise 1", 10, 3, 60, ExerciseType.FUERZA, List.of(MuscleGroup.PECHO)),
            new ExerciseDTO("Exercise 2", 15, 4, 45, ExerciseType.CARDIO, List.of(MuscleGroup.ESPALDA))
        ));

        PhysicalProgressDTO progressDTO = new PhysicalProgressDTO();
        progressDTO.setUserId(userDTO);
        progressDTO.setRoutine(routineDTO);
        progressDTO.setWeight(72.0F);
        progressDTO.setHeight(1.76F);
        progressDTO.setRegistrationDate(LocalDate.now());

        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressRepository.save(any(PhysicalProgress.class))).thenReturn(mockProgress);

        PhysicalProgress createdProgress = physicalProgressService.createPhysicalProgress(progressDTO);

        assertEquals(mockProgress, createdProgress);
        verify(physicalProgressRepository, times(1)).save(any(PhysicalProgress.class));
    }

    @Test
    void ShouldGetPhysicalProgressByUserId() {
        User mockUser = new User();
        mockUser.setId("user123");

        List<PhysicalProgress> mockProgressList = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());

        when(physicalProgressRepository.findByUserId(mockUser)).thenReturn(mockProgressList);

        List<PhysicalProgress> result = physicalProgressService.getPhysicalProgressByUserId(mockUser);

        assertEquals(2, result.size());
        verify(physicalProgressRepository, times(1)).findByUserId(mockUser);
    }

    @Test
    void ShouldGetPhysicalProgressByUserIdAndDate() {
        User mockUser = new User();
        mockUser.setId("user123");
        LocalDate date = LocalDate.now();

        List<PhysicalProgress> mockProgressList = List.of(new PhysicalProgress());

        when(physicalProgressRepository.findByUserIdAndRegistrationDate(mockUser, date)).thenReturn(mockProgressList);

        List<PhysicalProgress> result = physicalProgressService.getPhysicalProgressByUserIdAndDate(mockUser, date);

        assertEquals(1, result.size());
        verify(physicalProgressRepository, times(1)).findByUserIdAndRegistrationDate(mockUser, date);
    }

    @Test
    void ShouldGetPhysicalProgressByUserIdAndDateBetween() {
        User mockUser = new User();
        mockUser.setId("user123");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        List<PhysicalProgress> mockProgressList = Arrays.asList(new PhysicalProgress(), new PhysicalProgress(), new PhysicalProgress());

        when(physicalProgressRepository.findByUserIdAndRegistrationDateBetween(mockUser, startDate, endDate)).thenReturn(mockProgressList);

        List<PhysicalProgress> result = physicalProgressService.getPhysicalProgressByUserIdAndDateBetween(mockUser, startDate, endDate);

        assertEquals(3, result.size());
        verify(physicalProgressRepository, times(1)).findByUserIdAndRegistrationDateBetween(mockUser, startDate, endDate);
    }
}
