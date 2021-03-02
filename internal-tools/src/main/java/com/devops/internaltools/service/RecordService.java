package com.devops.internaltools.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.devops.internaltools.model.Record;
import com.devops.internaltools.repository.RecordRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javassist.bytecode.stackmap.BasicBlock.Catch;

@Service
@Transactional
public class RecordService {
    @Autowired
    private RecordRepo repo;

    // 取得所有 record
    public List<Record> listAll() {
        return repo.findAll();
    }

    // 透過 id 取得單一 record
    public Record getRecord(Integer id) {
        return repo.findById(id).get();
    }
    
    // 透過 company 和 service 取得 id (唯一辨識值)
    public ResponseEntity<?> getIdByServiceAndCompany(String svc , String com) {
        if(repo.findByServiceAndCompany(svc , com).isEmpty()) {
            return new ResponseEntity<String>("null" , HttpStatus.OK);
        } else {
            Record existsRecord = repo.findObjByServiceAndCompany(svc, com);
            return new ResponseEntity<Integer>(existsRecord.getId(),HttpStatus.OK);
        }
    }

    // 存入物件
    public ResponseEntity<?> saveNewCompany(Record com) {
        try {
            if(repo.findByServiceAndCompany(com.getService(),com.getCompany()).isEmpty()) {
                ZoneId zid = ZoneId.of("Asia/Taipei");
                ZonedDateTime now = ZonedDateTime.now(zid);
                System.out.print(now.toString());
                com.setDeployTime(now);
                repo.save(com);
                return new ResponseEntity<String>("success",HttpStatus.OK);
            }   else {
                System.out.print(com.getCompany().concat("/").concat(com.getService().concat(" is exists")));
                return new ResponseEntity<String>("error" ,HttpStatus.OK);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("error",HttpStatus.OK);
        }
    }

    // 更新版本
    public ResponseEntity<?> updateDeployVersionById(Integer id , String vs) {
        try {
            Record existCompany = repo.findById(id).get();
            if (existCompany!=null) {
                existCompany.setPreviousDeployTime(existCompany.getDeployTime());
                existCompany.setPreviousVersion(existCompany.getVersion());
                existCompany.setVersion(vs);
                return new ResponseEntity<String>("success",HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("null",HttpStatus.OK);
            }
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<String>("error",HttpStatus.OK);
        }
    }
    
    // 處理 microsoft post 表單的 post request
    public ResponseEntity<?> processDeploymentForm(Record postForm){
        try {
            if(repo.findByServiceAndCompany(postForm.getService(),postForm.getCompany()).isEmpty()){
                // not found mapping record so , post new record in table
                System.out.println("Not found the object in repo");
                repo.save(postForm);
                return new ResponseEntity<String>("success",HttpStatus.OK);
            } else {
                System.out.println("Found the object in repo");
                // get old record
                Record existsRecord = repo.findObjByServiceAndCompany(postForm.getService(),postForm.getCompany());
                // get now time
                ZoneId zid = ZoneId.of("Asia/Taipei");
                ZonedDateTime now = ZonedDateTime.now(zid);
                // update old record
                existsRecord.setPreviousDeployTime(existsRecord.getDeployTime());
                existsRecord.setPreviousVersion(existsRecord.getVersion());
                existsRecord.setDeployTime(now);
                existsRecord.setVersion(postForm.getVersion());
                return new ResponseEntity<String>("success",HttpStatus.OK);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("error",HttpStatus.OK);
        }
    }
    
    // 新增處理 microsoft 表單的 applier 和 form_uid
    public ResponseEntity<?> msForm(Record myform) {
        if((myform.getFormId() == null) || (myform.getApplier().isEmpty())){
            System.out.println("沒有 formId 和 applier 欄位");
            return new ResponseEntity<String>("error", HttpStatus.OK);
        }
        if((repo.findByServiceAndCompany(myform.getService(), myform.getCompany()).isEmpty())){
            System.out.println("資料庫沒有這筆資料 , 存入物件");
            ZoneId zid = ZoneId.of("Asia/Taipei");
            ZonedDateTime now = ZonedDateTime.now(zid);
            myform.setDeployTime(now);
            repo.save(myform);
            return new ResponseEntity<String>("success",HttpStatus.OK);
        } else {
            System.out.println("從資料庫撈出這筆 Record 並實體化");
            ZoneId zid = ZoneId.of("Asia/Taipei");
            ZonedDateTime now = ZonedDateTime.now(zid);
            Record existsRecord = repo.findObjByServiceAndCompany(myform.getService(), myform.getCompany());
            // 處理新欄位 formId 和 applier
            existsRecord.setApplier(myform.getApplier());
            existsRecord.setFormId(myform.getFormId());
            // 更新舊 record
            existsRecord.setPreviousDeployTime(existsRecord.getDeployTime());
            existsRecord.setPreviousVersion(existsRecord.getVersion());
            existsRecord.setDeployTime(now);
            existsRecord.setVersion(myform.getVersion());
            return new ResponseEntity<String>("success",HttpStatus.OK);
        }
    }
}