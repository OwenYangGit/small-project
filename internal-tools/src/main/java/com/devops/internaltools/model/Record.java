package com.devops.internaltools.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "version")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="COMPANY" , nullable = false)
    private String company;

    @Column(name="SERVICE" , nullable = false)
    private String service;

    @Column(name="VERSION" , nullable = false)
    private String vs;

    @Column(name="PREVIOUS_VERSION")
    private String pvs;

    @Column(name="UPLOADS_TIME" , columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private ZonedDateTime dt;

    @Column(name="PREVIOUS_UPLOADS_TIME")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private ZonedDateTime pdt;

    @Column(name="FORM_UID")
    private @Getter @Setter Integer formId;

    @Column(name="APPLIER")
    private @Getter @Setter String applier;

    protected Record() {
    }

    public Record(Integer id , String company , String service , String vs , String pvs , ZonedDateTime dt , ZonedDateTime pdt , Integer formId , String applier) {
        this.id = id;
        this.company = company;
        this.service = service;
        this.vs = vs;
        this.pvs = pvs;
        this.dt = dt;
        this.pdt = pdt;
        this.formId = formId;
        this.applier = applier;
    }

    public Integer getId() {
        return id;
    }
    
    public String getCompany(){
        return company;
    }

    public String getService() {
        return service;
    }

    public String getVersion() {
        return vs;
    }
    
    public String getPreviousVersion() {
        return pvs;
    }

    public ZonedDateTime getDeployTime() {
        return dt;
    }

    public ZonedDateTime getPreviousDeployTime() {
        return pdt;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setVersion(String vs) {
        this.vs = vs;
    }

    public void setPreviousVersion(String pvs) {
        this.pvs = pvs;
    }
    
    public void setDeployTime(ZonedDateTime dt) {
        this.dt = dt;
    }

    public void setPreviousDeployTime(ZonedDateTime pdt) {
        this.pdt = pdt;
    }
}