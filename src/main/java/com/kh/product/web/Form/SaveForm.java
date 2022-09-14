package com.kh.product.web.Form;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaveForm {
  @NotBlank
  private String pname;         //pname VARCHAR2(40) NOT NULL,
  @NotNull
  @PositiveOrZero
  @Size(min = 1, max = 10)
  private Integer count;        //count NUMBER(3)  NOT NULL,
  @NotNull
  @PositiveOrZero
  @Size(min = 1, max = 10)
  private Integer price;        //price NUMBER(30)   NOT NULL
}
