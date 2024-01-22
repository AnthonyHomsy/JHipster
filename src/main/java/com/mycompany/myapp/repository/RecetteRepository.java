package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Recette;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recette entity.
 */
@Repository
public interface RecetteRepository extends JpaRepository<Recette, Long> {
    @Query("select recette from Recette recette where recette.user.login = ?#{authentication.name}")
    List<Recette> findByUserIsCurrentUser();

    default Optional<Recette> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Recette> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Recette> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select recette from Recette recette left join fetch recette.user",
        countQuery = "select count(recette) from Recette recette"
    )
    Page<Recette> findAllWithToOneRelationships(Pageable pageable);

    @Query("select recette from Recette recette left join fetch recette.user")
    List<Recette> findAllWithToOneRelationships();

    @Query("select recette from Recette recette left join fetch recette.user where recette.id =:id")
    Optional<Recette> findOneWithToOneRelationships(@Param("id") Long id);
}
