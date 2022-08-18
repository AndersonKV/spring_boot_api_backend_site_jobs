package com.backend.demo.controller.matchingController;

import com.backend.demo.DTO.MatchingDTO;
import com.backend.demo.model.Matching;
import com.backend.demo.service.matching.MatchingFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/matchings")
@CrossOrigin("*")
@Api(value="API REST FIND APPLICATIONS")
public class matchingFindController {
    @Autowired
    private MatchingFindService matchingFindService;

    @ApiOperation(value="should find all applycations")
    @GetMapping(path = "list_applys")
    public ResponseEntity<List<Matching>> FindAll() {
        return this.matchingFindService.findAll();
    }

    @ApiOperation(value="should find by id")
    @GetMapping(path = "find_by_id")
    public ResponseEntity<Matching> FindById(@RequestParam("id") Long id) {
        return this.matchingFindService.findById(id);
    }

    @ApiOperation(value="should find by id, id_user, id_job")
    @GetMapping(path = "find_by_id_and_id_user_and_id_job")
    public ResponseEntity<Matching> FindByIdAndUserAndIdJob(@RequestBody MatchingDTO matchingDTO) {
        return this.matchingFindService.findByIdAndUserIdAndIdJob(matchingDTO.getId(), matchingDTO.getId_user(), matchingDTO.getId_job());
    }

    @ApiOperation(value="should find id_job")
    @GetMapping(path = "find_by_id_job")
    public  ResponseEntity<Matching>  FindByIdJob(@RequestParam("id_job") Long id_job) {
        return this.matchingFindService.findByIdJob(id_job);
    }

    @ApiOperation(value="should find id_user")
    @GetMapping(path = "find_by_id_user")
    public ResponseEntity<Matching>  FindByIdUser(@RequestParam("id_user") Long id_user) {
        return this.matchingFindService.findByIdUser(id_user);
    }
}
