/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Code {
    private Integer id;
    private String label;

    @Override
    public String toString() {
        return "Code{" + "id=" + id + ", label=" + label + ", code=" + code + ", recode=" + recode + ", createdtime=" + createdtime + '}';
    }
    private String code;
    private String recode;
    private Date createdtime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRecode(String recode) {
        this.recode = recode;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getCode() {
        return code;
    }

    public String getRecode() {
        return recode;
    }

    public Date getCreatedtime() {
        return createdtime;
    }
    
    
}
