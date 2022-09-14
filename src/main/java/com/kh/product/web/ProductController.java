package com.kh.product.web;

import com.kh.product.domain.Product;
import com.kh.product.domain.svc.ProductSVC;
import com.kh.product.web.Form.EditForm;
import com.kh.product.web.Form.ItemForm;
import com.kh.product.web.Form.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록화면
  @GetMapping("/save")
  public String saveForm(Model model) {
    model.addAttribute("form", new SaveForm());
    return "product/saveForm";
  }

  //등록처리
  @PostMapping("/save")
  public String save(
          @Valid @ModelAttribute("form") SaveForm saveForm,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes
  ){
    log.info("save={}",saveForm);

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "product/saveForm";
    }

    //필드검증
    //상품수량은 100초과 금지
    if(saveForm.getCount() > 100){
      bindingResult.rejectValue("count","product.count",new Integer[]{100},"상품수량 초과");
      log.info("bindingResult={}", bindingResult);
      return "product/saveForm";
    }

    //오브젝트검증
    //총액(상품수량*단가) 1000만원 초과금지
    if(saveForm.getCount() * saveForm.getPrice() > 10_000_000L){
      bindingResult.reject("product.totalPrice",new Integer[]{1000},"총액 초과!");
      log.info("bindingResult={}", bindingResult);
      return "product/saveForm";
    }

    Product product = new Product();
    BeanUtils.copyProperties(saveForm, product);
    Product pid = productSVC.save(product);

    redirectAttributes.addAttribute("id", pid);
    return "redirect:/products/{pid}";
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
    Optional<Product> findedProduct = Optional.ofNullable(productSVC.findById(pid));
    EditForm editForm = new EditForm();
    if(!findedProduct.isEmpty()) {
      BeanUtils.copyProperties(findedProduct.get(), editForm);
    }
    model.addAttribute("form", editForm);
    return "product/editForm";
  }

  //수정처리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long pid,
          @Valid @ModelAttribute("form") EditForm editForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
    if (bindingResult.hasErrors()) {
      return "product/updateForm";
    }

    //필드검증
    //상품수량은 100초과 금지
    if(editForm.getCount() > 100){
      bindingResult.rejectValue("count","product.count",new Integer[]{100},"상품수량 초과");
      log.info("bindingResult={}", bindingResult);
      return "product/saveForm";
    }

    //오브젝트검증
    //총액(상품수량*단가) 1000만원 초과금지
    if(editForm.getCount() * editForm.getPrice() > 10_000_000L){
      bindingResult.reject("product.totalPrice",new Integer[]{1000},"총액 초과!");
      log.info("bindingResult={}", bindingResult);
      return "product/saveForm";
    }

    Product product = new Product();
    BeanUtils.copyProperties(editForm, product);
    productSVC.update(pid, product);

    redirectAttributes.addAttribute("id", pid);
    return "redirect:/products/{pid}";
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
