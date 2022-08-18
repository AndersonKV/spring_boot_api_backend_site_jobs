package com.backend.demo.utils;

import com.backend.demo.enums.ContractTypes;
import com.backend.demo.enums.ExperiencieLevel;
import com.backend.demo.enums.Remote;
import com.backend.demo.enums.SizeCompany;
import com.backend.demo.model.Job;

import java.util.Collections;

public class JobFactory {

    public Job create(Long user_id, String token) {
        var job = new Job();


        job.setTitle("nome exemplo");
        job.setName_company("nome exemplo nome exemplo");
       job.setTechs("ruby, react");
//        job.setTech(Collections.singletonList(listTech));
        job.setResponsibilities("nome exemplo nome exemplo");
        job.setRequirements("nome exemplo nome exemplo");
        job.setTypes_contract(ContractTypes.clt);
        job.setSize_company(SizeCompany.media);
        job.setExperience_level(ExperiencieLevel.junior);
        job.setExpired_days("3");
        job.setSalary("300");
        job.setRemote(Remote.sim);
        job.setBenefits("nome exemplo nome exemplo");
        job.setToken(token);
        job.setUser_id(user_id);

        return job;
    }

    public Job update(Long user_id, long job_id, String token) {
        var job = new Job();

        String listTech = "php, c++, ruby";
        job.setTitle("lorem ipsum lorem ipsum");
        job.setName_company("lorem ipsum lorem ipsum");
//        job.setTechs(listTech);
//        job.setTech(Collections.singletonList(listTech));
        job.setResponsibilities("lorem ipsum lorem ipsum");
        job.setRequirements("lorem ipsum lorem ipsum");
        job.setTypes_contract(ContractTypes.clt);
        job.setSize_company(SizeCompany.media);
        job.setExperience_level(ExperiencieLevel.pleno);
        job.setExpired_days("3");
        job.setSalary("3000");
        job.setRemote(Remote.sim);
        job.setBenefits("lorem ipsum lorem ipsum");
        job.setToken(token);
        job.setUser_id(user_id);

        return job;
    }

    public Job updateWithRoleUser(Long user_id, Long job_id, String token) {
        var job = new Job();

        String listTech = "php, c++, ruby";
        job.setTitle("lorem ipsum lorem ipsum");
        job.setName_company("lorem ipsum lorem ipsum");
      job.setTechs("php, c++, ruby");
//        job.setTech(Collections.singletonList(listTech));
        job.setResponsibilities("lorem ipsum lorem ipsum");
        job.setRequirements("lorem ipsum lorem ipsum");
        job.setTypes_contract(ContractTypes.clt);
        job.setSize_company(SizeCompany.media);
        job.setExperience_level(ExperiencieLevel.pleno);
        job.setExpired_days("3");
        job.setSalary("3000");
        job.setRemote(Remote.sim);
        job.setBenefits("lorem ipsum lorem ipsum");
        job.setToken(token);
        job.setUser_id(user_id);

        return job;
    }

    public Job updateWithEmptyValues(Long user_id, Long job_id, String token) {
        var job = new Job();


        return job;
    }
}
