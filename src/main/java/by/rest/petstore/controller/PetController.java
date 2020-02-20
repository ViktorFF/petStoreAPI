package by.rest.petstore.controller;

import by.rest.petstore.exception.pet.PetNotFoundException;
import by.rest.petstore.exception.user.LoginUserException;
import by.rest.petstore.model.ApiRequest;
import by.rest.petstore.model.ApiResponse;
import by.rest.petstore.model.Pet;
import by.rest.petstore.service.PetService;
import by.rest.petstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/pet")
public class PetController {
    private UserService userService;
    private PetService petService;

    public PetController(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    @GetMapping(path = "/{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable Long petId,
                                      @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!petService.getPetsMap().containsKey(petId)) throw new PetNotFoundException();
        return new ResponseEntity<>(petService.getPetsMap().get(petId), HttpStatus.OK);
    }

    @PostMapping(path = "/{petId}")
    public ResponseEntity<ApiResponse> updatePetById(@PathVariable Long petId,
                                                     @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!petService.getPetsMap().containsKey(petId)) throw new PetNotFoundException();
        petService.petUpdateById(petId, request.getPetName(), request.getPetStatus());
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{petId}")
    public ResponseEntity<ApiResponse> deletePet(@PathVariable Long petId,
                                                 @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!petService.getPetsMap().containsKey(petId)) throw new PetNotFoundException();
        petService.getPetsMap().remove(petId);
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PostMapping(path = "/{petId}/uploadImage")
    public ResponseEntity<ApiResponse> uploadImageFile(@PathVariable Long petId,
                                                       @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!petService.getPetsMap().containsKey(petId)) throw new PetNotFoundException();
        petService.getPetsMap().get(petId).getPhotoUrls().add(request.getPetPhotoUrl());
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addPet(@Valid @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        petService.addPet(request.getPet());
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updatePet(@Valid @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!petService.updatePet(request.getPet())) throw new PetNotFoundException();
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @GetMapping(path = "/findByStatus")
    public ResponseEntity<List<Pet>> findByStatus(@RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        return new ResponseEntity<>(petService.findByStatus(request.getPetStatus()), HttpStatus.OK);
    }
}
