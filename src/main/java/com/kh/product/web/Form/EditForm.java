package com.kh.product.web.Form;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EditForm {
  private Long pid;             //pid   NUMBER  (8)  PRIMARY Key,
  private String pname;         //pname VARCHAR2(40) NOT NULL,
  @Size(min = 1, max=100)
  private Integer count;        //count NUMBER(3)  NOT NULL,
  @Min(1)
  private Integer price;        //price NUMBER(30)   NOT NULL
}
