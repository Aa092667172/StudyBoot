package com.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.EmployeeVO;

@Repository
public interface EmployeeService extends JpaRepository<EmployeeVO, Long> {

}
