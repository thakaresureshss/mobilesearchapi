package com.mobile.search.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Suresh
 */

@Entity
@Table(name = "role")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code", unique = true)
  private String code;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private String status;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  public Role(String code) {
    super();
    this.code = code;
  }

  public void setCode(String code) {
    this.code = code.toUpperCase();
  }
}
