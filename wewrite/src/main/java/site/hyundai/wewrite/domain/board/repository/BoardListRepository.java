package site.hyundai.wewrite.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.hyundai.wewrite.domain.entity.Board;

import javax.persistence.Tuple;
import java.util.List;

/**
 * @author 이소민
 */

@Repository
public interface BoardListRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT * FROM TABLE(BOARD_PACKAGE.GET_POPULAR_BOARDS(:p_group_id, :p_sorted_type))", nativeQuery = true)
    List<Tuple> getPopularBoards(@Param("p_group_id") Long groupId, @Param("p_sorted_type") String sortedType);

}

