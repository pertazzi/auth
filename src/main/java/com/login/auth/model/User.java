package com.login.auth.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Data
public class User {
	@Id
	@Column
	private String fiscalCode;
	@Column
	@NotNull
	private String username;
	@Column
	@NotNull
	private String password;
	@Column
	@NotNull
	private String name;
	@Column
	@NotNull
	private String surname;
}
