package com.backend.demo.controller.jobController;

import com.backend.demo.DTO.DataJobSuccessDTO;
import com.backend.demo.DTO.UserDTO;
import com.backend.demo.model.Job;
import com.backend.demo.service.job.JobFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/jobs")
@CrossOrigin(origins = "*")
@Api(value = "API REST FIND JOB")
public class JobFindController {
    @Autowired
    private JobFindService jobFindService;

    @ApiOperation(value = "should get list jobs")
    @GetMapping(path = "list_jobs")
    public ResponseEntity findAll() {
        return this.jobFindService.findAll();
    }

    @ApiOperation(value = "should find job by id")
    @GetMapping(path = "find_by_id")
    public ResponseEntity<Job> FindJobById(@RequestParam("id") Long id) {
        return this.jobFindService.findById(id);
    }

    @ApiOperation(value = "should find by user id")
    @GetMapping(path = "find_by_id_user")
    public ResponseEntity FindByUserId(@RequestParam("id") Long id) {
        return this.jobFindService.findByUserId(id);
    }


//    @GetMapping(path = "find_by_tech")
//    public List<Job> FindByTech(
//            @RequestParam(value = "tech", required = false) String tech,
//            @RequestParam(value = "type_contract", required = false) String type_contract,
//            @RequestParam(value = "experience_level", required = false) String experience_level,
//            @RequestParam(value = "remote", required = false) String remote) {
//
//        return this.jobFindService.findByTech(tech, type_contract, remote);
//
//    }

    @GetMapping(path = "find_by_tech")
    public ResponseEntity<List<Job>> FindByTech(@RequestParam(value = "tech") String tech) {
        return this.jobFindService.findByTech(tech);

    }

    @ApiOperation(value = "get three last jobs")
    @GetMapping(path = "find_the_last_three_jobs")
    public ResponseEntity<List<Job>> getTheLastThreeJobs() {
        return this.jobFindService.findTheLastThreeJobs();
    }

}
