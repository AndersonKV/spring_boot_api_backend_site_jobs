package com.backend.demo.util;

import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateValidation {
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/MM/yyyy");

    public void pass(String created_at) {

        if (LocalDate.now().format(formatterDate).compareTo(created_at) < 0) {
            throw new ApiRequestException("A data para inscrição da vaga expirou");
        }

    }
}
