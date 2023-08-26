package com.ECommerceWeb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;

    @Column(name="street_address")
    private String streetAddress;

    @Column(name="state")
    private String state;

    @Column(name="city")
    private String city;

    @Column(name="zip_code")
    private String zipCode;


    @ManyToOne
    @JoinColumn(name="user_id")
     @JsonIgnore
    private  User user;

    private  String mobile;


   public Address(){

   }

    public Address(Long id, String firstName, String lastName, String streetAddress, String state, String city, String zipCode, User user, String mobile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.user = user;
        this.mobile = mobile;
    }
}
