package com.backend.demo.controller.matchingController;

import com.backend.demo.service.matching.MatchingDeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/matchings")
@CrossOrigin("*")
@Api(value = "API REST DELETE APPLICATIONS")
public class matchingDeleteController {

    @Autowired
    private MatchingDeleteService matchingDeleteService;

    @DeleteMapping("/delete_by_id/{id}")
    public ResponseEntity DeleteById(@PathVariable(value = "id") Long orderId) {
        return this.matchingDeleteService.deleteById(orderId);
    }

//    @ApiOperation(value = "should delete by id")
//    @DeleteMapping("/delete_by_id")
//    public ResponseEntity DeleteById(@RequestParam("id") Long id) {
//        return this.applyDeleteService.deleteById(id);
//    }


    @ApiOperation("should delete by id, id_user, id_job")
    @DeleteMapping(value = "/delete")
    public ResponseEntity DeleteByIdAndIdUserAndIdJob(
            @RequestParam("id") Long id,
            @RequestParam("id_user") Long id_user,
            @RequestParam("id_job") Long id_job) {
        return this.matchingDeleteService.deleteByIdAndIdUserAndIdJob(id, id_user, id_job);
    }

    @ApiOperation(value = "should destroyer applications")
    @DeleteMapping(path = "destroyer")
    public ResponseEntity destroyer() {
        return this.matchingDeleteService.destroyer();
    }


}
