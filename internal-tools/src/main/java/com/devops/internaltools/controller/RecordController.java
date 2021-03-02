package com.devops.internaltools.controller;


import java.util.List;

import com.devops.internaltools.service.RecordService;
import com.devops.internaltools.model.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;



@RestController
public class RecordController {
    @Autowired RecordService service;
    
    @GetMapping(value = "/records")
    public List<Record> listAllRecords(){
        return service.listAll();
    }
    
    @GetMapping(value = "/record/{id}")

    public Record getRecord(@PathVariable Integer id){
        return service.getRecord(id);
    }

    @GetMapping(value = "/record/id")
    public ResponseEntity<?> getRecordId(@RequestParam(value = "service") String svc , @RequestParam(value = "company") String com){
        return service.getIdByServiceAndCompany(svc, com);
    }

    @PostMapping(value = "/record")
    public ResponseEntity<?> saveRecord(@RequestBody Record record) {
        return service.saveNewCompany(record);
    }

    @PutMapping(value = "/record/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable Integer id , @RequestParam(value = "version") String vs){
        return service.updateDeployVersionById(id, vs);
    }

    @PostMapping(value = "/record/form")
    public ResponseEntity<?> processForm(@RequestBody Record form) {
        return service.processDeploymentForm(form);
    }

    @PostMapping(value = "/ms/form")
    public ResponseEntity<?> saveForm(@RequestBody Record form) {
        return service.msForm(form);
    }
}