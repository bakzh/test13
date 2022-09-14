package com.kh.product.web.Form;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EditForm {
  private Long pid;             //pid   NUMBER  (8)  PRIMARY Key,
  @NotBlank
  private String pname;         //pname VARCHAR2(40) NOT NULL,
  @NotNull
  @PositiveOrZero
  private Integer count;        //count NUMBER(3)  NOT NULL,
  @NotNull
  @PositiveOrZero
  private Integer price;        //price NUMBER(30)   NOT NULL
}
