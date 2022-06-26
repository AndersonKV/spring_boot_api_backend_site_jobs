package com.backend.demo.controller.applyController;

import com.backend.demo.DTO.DataDeleteDTO;
import com.backend.demo.service.apply.ApplyDeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/matchings")
@CrossOrigin("*")
@Api(value="API REST DELETE APPLICATIONS")
public class applyDeleteController {

    @Autowired
    private ApplyDeleteService applyDeleteService;

    @ApiOperation(value="should delete by id")
    @DeleteMapping(value = "delete_by_id/{id}")
    public void DeleteById(@PathVariable Long id) {
        this.applyDeleteService.deleteById(id);
    }


    @ApiOperation(value="should delete by id, id_user, id_job")
    @DeleteMapping(value = "delete")
    public void DeleteByIdAndIdUserAndIdJob(
            @RequestParam("id") Long id,
            @RequestParam("id_user") Long id_user,
            @RequestParam("id_job") Long id_job) {
        this.applyDeleteService.deleteByIdAndIdUserAndIdJob(id, id_user, id_job);
    }

    @ApiOperation(value="should destroyer applications")
    @DeleteMapping(path = "destroyer")
    public void destroyer() {
        this.applyDeleteService.destroyer();
    }


}
