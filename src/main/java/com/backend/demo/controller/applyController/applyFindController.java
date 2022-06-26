package com.backend.demo.controller.applyController;

import com.backend.demo.DTO.ApplyDTO;
import com.backend.demo.model.Apply;
import com.backend.demo.service.apply.ApplyFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/matchings")
@CrossOrigin("*")
@Api(value="API REST FIND APPLICATIONS")
public class applyFindController {
    @Autowired
    private ApplyFindService applyFindService;

    @ApiOperation(value="should find all applycations")
    @GetMapping(path = "find_all")
    public List<Apply> FindAll() {
        return this.applyFindService.findAll();
    }

    @ApiOperation(value="should find by id")
    @GetMapping(path = "find_by_id")
    public Optional<Apply> FindById(@RequestParam("id") Long id) {
        return this.applyFindService.findById(id);
    }

    @ApiOperation(value="should find by id, id_user, id_job")
    @GetMapping(path = "find_by_id_and_id_user_and_id_job")
    public Optional<Apply> FindByIdAndUserAndIdJob(@RequestBody ApplyDTO applyDTO) {
        return this.applyFindService.findByIdAndUserIdAndIdJob(applyDTO.getId(), applyDTO.getId_user(), applyDTO.getId_job());
    }

    @ApiOperation(value="should find id_job")
    @GetMapping(path = "find_by_id_job")
    public  Optional<Apply>  FindByIdJob(@RequestParam("id_job") Long id_job) {
        return this.applyFindService.findByIdJob(id_job);
    }

    @ApiOperation(value="should find id_user")
    @GetMapping(path = "find_by_id_user")
    public Optional<Apply> FindByIdUser(@RequestParam("id_user") Long id_user) {
        return this.applyFindService.findByIdUser(id_user);
    }
}
