package com.ktg.web.repository;

import com.ktg.web.domain.ActiveItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveItemsRepository  extends JpaRepository<ActiveItems, String> {
}
