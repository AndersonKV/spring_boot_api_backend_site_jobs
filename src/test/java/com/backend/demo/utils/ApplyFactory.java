package com.backend.demo.utils;

import com.backend.demo.model.Matching;

public class ApplyFactory {

    public Matching create(Long id_user, Long id_job, String token) {
        var apply = new Matching();

        apply.setId_user(id_user);
        apply.setId_job(id_job);
        apply.setToken(token);

        return apply;
     }
}
