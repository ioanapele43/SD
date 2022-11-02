package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Users;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserDTO> findUsers(){
        List<Users> users =userRepository.findAll();
        return users.stream().map(UserBuilder::toUserDTO).collect(Collectors.toList());
    }
    public UserDTO findById(UUID id){
        Optional<Users> userOptional=userRepository.findById(id);
        if(!userOptional.isPresent()){
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(Users.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(userOptional.get());
    }
    public UUID insert(UserDTO userDTO){
        Users users =UserBuilder.toEntity(userDTO);
        users =userRepository.save(users);
        LOGGER.debug("User with id {} was inserted in db", users.getId());
        return users.getId();
    }
    public UUID update(UserDTO userDTO,UUID id){
        Users users =UserBuilder.toEntity(userDTO);
        users.setId(id);
        users =userRepository.save(users);
        LOGGER.debug("User with id {} was updated in db", users.getId());
        return users.getId();
    }
    public void delete(UUID id){
        Optional<Users> user=userRepository.findById(id);
        if(user.isPresent()){
            LOGGER.debug("User with id {} is deleted from db", user.get().getId());
            userRepository.delete(user.get());
        }
    }
}
