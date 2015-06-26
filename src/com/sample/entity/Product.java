/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.base.entity.Idable;

@Entity
@Table(name="sample_product")
public class Product implements Idable<Long>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	/**
	 * 产品名称
	 */
    @Column(nullable=false, length=20)
    private String name;
    
	/**
	 * 产品描述
	 */
    @Column(length=500)
    private String description;
    
	/**
	 * 产品等级
	 */
    @Column(nullable=false, length=10)
    private String level;
    
	/**
	 * 创建人
	 */
    @Column(nullable=false, length=19)
    private Long userId;
    
	/**
	 * 创建时间
	 */
    @Column(length=19)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param name the name to set
	 */
    public void setName(String name){
       this.name = name;
    }
    
    /**
     * @return the name 
     */
    public String getName(){
       return this.name;
    }
	
	/**
	 * @param description the description to set
	 */
    public void setDescription(String description){
       this.description = description;
    }
    
    /**
     * @return the description 
     */
    public String getDescription(){
       return this.description;
    }
	
	/**
	 * @param level the level to set
	 */
    public void setLevel(String level){
       this.level = level;
    }
    
    /**
     * @return the level 
     */
    public String getLevel(){
       return this.level;
    }
	
	/**
	 * @param userId the userId to set
	 */
    public void setUserId(Long userId){
       this.userId = userId;
    }
    
    /**
     * @return the userId 
     */
    public Long getUserId(){
       return this.userId;
    }
	
	/**
	 * @param createTime the createTime to set
	 */
    public void setCreateTime(Date createTime){
       this.createTime = createTime;
    }
    
    /**
     * @return the createTime 
     */
    public Date getCreateTime(){
       return this.createTime;
    }
}
