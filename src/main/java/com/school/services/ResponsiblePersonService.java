package com.school.services;

import com.school.models.ResponsiblePerson;

public interface ResponsiblePersonService {
    void save(ResponsiblePerson responsiblePerson);
    void delete(ResponsiblePerson responsiblePerson);
    ResponsiblePerson responsiblePersonFindById(Long id);

}
