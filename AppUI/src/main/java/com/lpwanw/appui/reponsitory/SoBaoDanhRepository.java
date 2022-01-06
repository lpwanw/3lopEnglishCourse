package com.lpwanw.appui.reponsitory;

import com.lpwanw.appui.Entity.KhoaThi;
import com.lpwanw.appui.Entity.SoBaoDanh;
import com.lpwanw.appui.Entity.Student;
import com.lpwanw.appui.Entity.TrinhDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoBaoDanhRepository extends JpaRepository<SoBaoDanh, Integer> {
    List<SoBaoDanh> findSoBaoDanhByKhoaThiAndTrinhDo(KhoaThi khoaThi, TrinhDo trinhDo);
    List<SoBaoDanh> findSoBaoDanhByNguoidangky(Student nguoidangky);
}