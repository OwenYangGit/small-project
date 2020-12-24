package com.devops.internaltools.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
            return new ResponseEntity<String>("Not found" , HttpStatus.NOT_FOUND);
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
                return new ResponseEntity<>(HttpStatus.OK);
            }   else {
                System.out.print(com.getCompany().concat("/").concat(com.getService().concat(" is exists")));
                return new ResponseEntity<Record>(com ,HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}