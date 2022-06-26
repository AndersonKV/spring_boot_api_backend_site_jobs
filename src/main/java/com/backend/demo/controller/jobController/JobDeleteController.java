package com.backend.demo.controller.jobController;

import com.backend.demo.model.Job;
import com.backend.demo.service.job.JobDeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/jobs")
@CrossOrigin(origins = "*")
@Api(value="API REST DELETE USER")
public class JobDeleteController {
    @Autowired
    private JobDeleteService jobDeleteService;

    @ApiOperation(value="should delete job")
    @DeleteMapping(path = "delete_by_id")
    public void DeleteByID(@RequestParam("id") Long id) {
        this.jobDeleteService.delete_by_id(id);
    }

    @ApiOperation(value="should destroyer job")
    @DeleteMapping(path = "destroyer")
    public void DeleteAllVacancy() {
        this.jobDeleteService.destroyer();
    }


}
