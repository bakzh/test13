package com.kh.product.web.Form;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {
  private Long pid;             //pid   NUMBER  (8)  PRIMARY Key,
  private String pname;         //pname VARCHAR2(40) NOT NULL,
  private Integer count;        //count NUMBER(3)  NOT NULL,
  private Integer price;        //price NUMBER(30)   NOT NULL
}
