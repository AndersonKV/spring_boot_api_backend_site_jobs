package com.backend.demo.util;

import com.backend.demo.DTO.JobDTO;
import com.backend.demo.enums.*;
import com.backend.demo.exception.ApiRequestException;
import com.backend.demo.model.Job;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class JobValidation {
    @Autowired
    private UserRepository userRepository;

    public void pass(Job request) {
        Optional<User> user = this.userRepository.findById(request.getUser_id());

        if (!user.isPresent()) {
            throw new ApiRequestException("id " + request.getUser_id() + " do usúario não encontrado");
        }

        if (user.get().getRole() != UserRole.company) {
            throw new ApiRequestException("você não tem autorização para postar");
        }

        if (request.getTitle().trim().length() < 1 ) {
            throw new ApiRequestException("O titulo deve ter pelo menos 1  caracteres");
        }

        if (request.getSize_company() != SizeCompany.pequena && request.getSize_company() != SizeCompany.media && request.getSize_company() != SizeCompany.grande) {
            throw new ApiRequestException("O nome da empresa ");
        }

        if (request.getExperience_level() != ExperiencieLevel.junior &&
                request.getExperience_level() != ExperiencieLevel.pleno &&
                request.getExperience_level() != ExperiencieLevel.senior) {
            throw new ApiRequestException("Error ao selecionar o nivel do programador");
        }

        if (request.getTypes_contract() != ContractTypes.clt && request.getTypes_contract() != ContractTypes.pj) {
            throw new ApiRequestException("não foi especificado se o tipo de contrato é clt ou pj");
        }

        if (request.getRemote() != Remote.sim && request.getRemote() != Remote.não) {
            throw new ApiRequestException("Problema ao seleciona se a vaga aceita remoto ou não");
        }
    }

    public Job update(Job request, User user) {


        if (user.getRole() != UserRole.company) {
            throw new ApiRequestException("você não tem autorização para atualizar");
        }

        if (request.getTitle().trim().length() < 1 ) {
            throw new ApiRequestException("O titulo deve ter pelo menos 1  caracteres");
        }

        if (request.getSize_company() != SizeCompany.pequena && request.getSize_company() != SizeCompany.media && request.getSize_company() != SizeCompany.grande) {
            throw new ApiRequestException("O nome da empresa ");
        }

        if (request.getExperience_level() != ExperiencieLevel.junior &&
                request.getExperience_level() != ExperiencieLevel.pleno &&
                request.getExperience_level() != ExperiencieLevel.senior) {
            throw new ApiRequestException("Error ao selecionar o nivel do programador");
        }

        if (request.getTypes_contract() != ContractTypes.clt && request.getTypes_contract() != ContractTypes.pj) {
            throw new ApiRequestException("não foi especificado se o tipo de contrato é clt ou pj");
        }

        if (request.getRemote() != Remote.sim && request.getRemote() != Remote.não) {
            throw new ApiRequestException("Problema ao seleciona se a vaga aceita remoto ou não");
        }
        request.setId(user.getId());

        return request;
    }
}
