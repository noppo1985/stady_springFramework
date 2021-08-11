package com.example.domain.user.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;


@SuppressWarnings("serial")
@Embeddable
@Data
public class SalaryKey implements Serializable{
	private String userId;
	private String yearMonth;
	
}
