package com.school.services.impl;

import com.school.models.ResponsiblePerson;
import com.school.repositories.ResponsiblePersonRepository;
import com.school.services.ResponsiblePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsiblePersonServiceImpl implements ResponsiblePersonService {

    @Autowired
    private ResponsiblePersonRepository responsiblePersonRepository;

    @Override
    public void save(ResponsiblePerson responsiblePerson) {
        responsiblePersonRepository.save(responsiblePerson);
    }

    @Override
    public void delete(ResponsiblePerson responsiblePerson) {
        responsiblePersonRepository.delete(responsiblePerson);
    }

    @Override
    public ResponsiblePerson responsiblePersonFindById(Long id) {
        return responsiblePersonRepository.findOne(id);
    }
}
