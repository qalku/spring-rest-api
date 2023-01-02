package pl.mw.restapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mw.restapi.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where title = :title")
    //List<Post> findAllByTitle(String title);
    List<Post> findAllByTitle(@Param("title") String title);

    // problem n+1
    @Query("Select p from Post p ")
    List<Post> findAllPosts(Pageable page); // << stronicowanie Pageable //"left join fetch p.comment")
}
