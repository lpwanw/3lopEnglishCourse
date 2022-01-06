package com.lpwanw.appui.reponsitory;

import com.lpwanw.appui.Entity.TrinhDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrinhDoRepository extends JpaRepository<TrinhDo, Integer> {
    @Query("select t from TrinhDo t where t.ten = ?1")
    TrinhDo findByTen(String ten);
}