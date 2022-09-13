package com.kh.product.domain.dao;

import com.kh.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{

  private final JdbcTemplate jt;
  /**
   * 등록
   * @param product 상품정보
   * @return 등록된 상품정보
   */
  @Override
  public int save(Product product) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product VALUES(? ,? ,? ,?) ");

    result = jt.update(sql.toString(),product.getPid(),product.getPname(),product.getCount(),product.getPrice());

    return result;
  }

  /**
   * 조회
   *
   * @param pid 상품아이디
   * @return 상품
   */
  @Override
  public Product findById(Long pid) {
    StringBuffer sql = new StringBuffer();
    sql.append("select pid,pname,count,price");
    sql.append("  from product ");
    sql.append(" where pid = ? ");

    Product findedProduct = null;

    try{
      findedProduct = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Product.class), pid);
    }catch (DataAccessException e){
      log.info("찾고자하는 상품이 없습니다={}",pid);
    }
    return findedProduct;
  }

  /**
   * 수정
   *
   * @param pid     상품아이디
   * @param product 수정할 상품정보
   * @return
   */
  @Override
  public int update(Long pid, Product product) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("update product");
    sql.append("   set pname = ?," );
    sql.append("       count = ?," );
    sql.append("       price = ?" );
    sql.append(" where pid = ?" );

    result = jt.update(sql.toString(),product.getPname(),product.getCount(),product.getPrice(),product.getPid());

    return result;
  }

  /**
   * 삭제
   *
   * @param pid 상품아이디
   * @return
   */
  @Override
  public int delete(Long pid) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("delete product where pid = ? ");

    result = jt.update(sql.toString(),pid);

    return result;
  }

  @Override
  public List<Product> findAll() {

    StringBuffer sql = new StringBuffer();
    sql.append("select pid,pname,count,price ");
    sql.append("  from product ");

    return jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
  }

  /**
   * 상품아이디생성
   */
  @Override
  public Long generatePid() {
    String sql = "select product_pid_SEQ.nextval from dual";
    Long pid = jt.queryForObject(sql, Long.class);   //단일레코드, 단일컬럼
    return pid;
  }
}
