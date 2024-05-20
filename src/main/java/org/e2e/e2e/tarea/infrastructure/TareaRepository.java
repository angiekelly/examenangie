package org.e2e.e2e.tarea.infrastructure;

import jakarta.transaction.Transactional;
import org.e2e.e2e.tarea.domain.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
