package com.devops.internaltools.repository;

import java.util.List;

import com.devops.internaltools.model.Record;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepo extends JpaRepository<Record , Integer> {
    List<Record> findByService(String service);
    List<Record> findByServiceAndCompany(String service , String company);
    Record findObjByServiceAndCompany(String service , String company);
    // Boolean existsByServicBoolean(String service);
    // Boolean existsByCompanyBoolean(String company);
}