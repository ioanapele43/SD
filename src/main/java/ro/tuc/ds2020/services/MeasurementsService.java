package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.MeasurementsDTO;
import ro.tuc.ds2020.dtos.builders.MeasurementsBuilder;
import ro.tuc.ds2020.entities.Measurements;
import ro.tuc.ds2020.repositories.MeasurementsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeasurementsService {
    private static final Logger LOGGER= LoggerFactory.getLogger(MeasurementsService.class);
    private final MeasurementsRepository measurementsRepository;
    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }
    public List<MeasurementsDTO> findMeasurements(){
        List<Measurements> measurements=measurementsRepository.findAll();
        return measurements.stream().map(MeasurementsBuilder::toMeasurementsDTO).collect(Collectors.toList());
    }
    public MeasurementsDTO findById(UUID id){
        Optional<Measurements> measurementsOptional=measurementsRepository.findById(id);
        if(!measurementsOptional.isPresent()){
            LOGGER.error("Measurement with id {} was not found in db", id);
            throw new ResourceNotFoundException(Measurements.class.getSimpleName() + " with id: " + id);
        }
        return MeasurementsBuilder.toMeasurementsDTO(measurementsOptional.get());
    }
    public UUID insert(MeasurementsDTO measurementsDTO){
        Measurements measurements=MeasurementsBuilder.toEntity(measurementsDTO);
        measurements=measurementsRepository.save(measurements);
        LOGGER.debug("Measurement with id {} was inserted in db", measurements.getId());
        return measurements.getId();
    }
    public UUID update(MeasurementsDTO measurementsDTO, UUID id){
        Measurements measurements=MeasurementsBuilder.toEntity(measurementsDTO);
        measurements.setId(id);
        measurements=measurementsRepository.save(measurements);
        LOGGER.debug("Measurement with id {} was updated in db", measurements.getId());
        return measurements.getId();
    }
    public void delete(UUID id){
        Optional<Measurements> measurements=measurementsRepository.findById(id);
       if(measurements.isPresent()){
           LOGGER.debug("Measurement with id {} is deleted from db", measurements.get().getId());
           measurementsRepository.delete(measurements.get());
       }
    }

}
