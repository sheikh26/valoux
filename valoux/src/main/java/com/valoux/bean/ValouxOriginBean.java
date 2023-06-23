package com.valoux.bean;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the valoux_origin database table.
 * 
 */
@Entity
@Table(name="valoux_origin")
@NamedQuery(name="ValouxOriginBean.findAll", query="SELECT v FROM ValouxOriginBean v")
public class ValouxOriginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=2)
	private String iso;

	@Column(length=3)
	private String iso3;

	@Column(nullable=false, length=80)
	private String name;

	@Column(nullable=false, length=80)
	private String nicename;

	@Column(name = "numcode", columnDefinition = "SMALLINT DEFAULT 0")
	private Integer numcode;

	@Column(nullable=false)
	private Integer phonecode;

	public ValouxOriginBean() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIso() {
		return this.iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getIso3() {
		return this.iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNicename() {
		return this.nicename;
	}

	public void setNicename(String nicename) {
		this.nicename = nicename;
	}

	public Integer getNumcode() {
		return this.numcode;
	}

	public void setNumcode(Integer numcode) {
		this.numcode = numcode;
	}

	public Integer getPhonecode() {
		return this.phonecode;
	}

	public void setPhonecode(Integer phonecode) {
		this.phonecode = phonecode;
	}

}