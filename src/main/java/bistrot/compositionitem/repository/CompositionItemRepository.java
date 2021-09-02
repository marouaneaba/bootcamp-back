package bistrot.compositionitem.repository;

import bistrot.compositionitem.entity.CompositionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompositionItemRepository extends JpaRepository<CompositionItem, Long> {

  List<CompositionItem> findAllByName(String name);
}
