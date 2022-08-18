package com.backend.demo.controller.jobController;

import com.backend.demo.service.job.JobDeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/jobs")
@CrossOrigin(origins = "*")
@Api(value="API REST DELETE USER")
public class JobDeleteController {
    @Autowired
    private JobDeleteService jobDeleteService;

    @ApiOperation(value="should delete job")
    @DeleteMapping(value = "delete/{id}")
    public  ResponseEntity DeleteByID(@PathVariable("id") Long id) {
        return this.jobDeleteService.delete_by_id(id);
    }

    @ApiOperation(value="should destroyer job")
    @DeleteMapping(path = "destroyer")
    public ResponseEntity   DeleteAllVacancy() {
       return this.jobDeleteService.destroyer();
    }


}
