package finalSearchProject.fjpEx.repository;

import finalSearchProject.fjpEx.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageRepository  extends JpaRepository<Page, Integer> {
    @Query(value = "SELECT * FROM page p WHERE p.id = ?1"
            , nativeQuery = true)
    Page getById(int id);

    @Query(value = "SELECT EXISTS(SELECT * FROM page p WHERE p.path = ?1)"
            , nativeQuery = true)
    int existsByPath(String path);
}
