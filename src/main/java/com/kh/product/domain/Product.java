package com.kh.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Long pid;             //pid   NUMBER  (8)  PRIMARY Key,
  private String pname;         //pname VARCHAR2(40) NOT NULL,
  private Integer count;        //count NUMBER(3)  NOT NULL,
  private Integer price;        //price NUMBER(30)   NOT NULL
}
