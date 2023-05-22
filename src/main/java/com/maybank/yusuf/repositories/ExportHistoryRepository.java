/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maybank.yusuf.repositories;

import com.maybank.yusuf.models.ExportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 62878
 */
@Repository
public interface ExportHistoryRepository extends JpaRepository<ExportHistory, Long> {
}