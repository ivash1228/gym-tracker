package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.AddSetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/sets")
public class SetController {

    @PostMapping
    public ResponseEntity<UUID> addSet(@RequestBody AddSetRequest addSetRequest) {

    }

}
