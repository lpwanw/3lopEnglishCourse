package com.lpwanw.appui.reponsitory;

import com.lpwanw.appui.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByHoTenContainsAndSdtContains(String hoTen, String sdt);
}