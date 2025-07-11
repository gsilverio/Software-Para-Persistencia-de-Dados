package com.persist.data.repositorie;

import com.persist.data.model.Category;
import com.persist.data.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
