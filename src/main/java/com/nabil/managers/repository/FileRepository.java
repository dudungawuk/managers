package com.nabil.managers.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.nabil.managers.entity.File;

public interface FileRepository extends JpaRepository<File,String> {

    
}
