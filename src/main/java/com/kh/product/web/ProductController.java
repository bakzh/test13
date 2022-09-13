package com.kh.product.web;

import com.kh.product.domain.Product;
import com.kh.product.domain.svc.ProductSVC;
import com.kh.product.web.Form.EditForm;
import com.kh.product.web.Form.ItemForm;
import com.kh.product.web.Form.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록화면
  @GetMapping("/save")
  public String saveForm(){

    return "product/saveForm";
  }

  //등록처리
  @PostMapping("/save")
  public String save(SaveForm saveForm){
    log.info("save={}",saveForm);

    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setCount(saveForm.getCount());
    product.setPrice(saveForm.getPrice());

    Product savedProduct = productSVC.save(product);
    return "redirect:/products/" + savedProduct.getPid();
  }
  //조회
  @GetMapping("/{pid}")
  public String findById(@PathVariable("pid") Long pid, Model model){
    Product findedProduct = productSVC.findById(pid);

    ItemForm itemForm = new ItemForm();
    itemForm.setPid(findedProduct.getPid());
    itemForm.setPname(findedProduct.getPname());
    itemForm.setCount(findedProduct.getCount());
    itemForm.setPrice(findedProduct.getPrice());

    model.addAttribute("itemForm",itemForm);
    return "product/itemForm";
  }

  //수정화면
  @GetMapping("/{pid}/edit")
  public String updateForm(@PathVariable("pid") Long pid, Model model){
    Product findedProduct = productSVC.findById(pid);

    EditForm editForm = new EditForm();
    editForm.setPid(findedProduct.getPid());
    editForm.setPname(findedProduct.getPname());
    editForm.setCount(findedProduct.getCount());
    editForm.setPrice(findedProduct.getPrice());

    model.addAttribute("editForm",editForm);
    return "product/editForm";
  }

  //수정처리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long pid, EditForm editForm){
    log.info("edit={}",editForm);

    Product product = new Product();
    product.setPid(pid);
    product.setPname(editForm.getPname());
    product.setCount(editForm.getCount());
    product.setPrice(editForm.getPrice());

    productSVC.update(pid,product);
    return "redirect:/products/" + pid;
  }

  //삭제
  @GetMapping("/{pid}/del")
  public String delete(@PathVariable("pid") Long pid){

    productSVC.delete(pid);
    return "redirect:/products";
  }

  //목록
  @GetMapping
  public String list(Model model){

    List<Product> list = productSVC.findAll();
    model.addAttribute("list",list);
    return "product/all";
  }
}
