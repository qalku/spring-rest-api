package pl.mw.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.restapi.model.Comment;
import pl.mw.restapi.model.Post;
import pl.mw.restapi.repository.CommentRepository;
import pl.mw.restapi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private static final int PAGE_SIZE=20;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Cacheable(cacheNames = "Posts")
    public List<Post> getPosts(int page, Sort.Direction sort){
        //return postRepository.findAllPosts(PageRequest.of(page,PAGE_SIZE, Sort.by(Sort.Order.asc("id"),Sort.Order.desc("created"))));
        return postRepository.findAllPosts(
                PageRequest.of(page,PAGE_SIZE,
                        Sort.by(sort, "id")
                )
        ); //<< stronicowanie i sortowanie
    }

    @Cacheable(cacheNames = "SinglePost", key = "#id")
    public Post getSinglePost(long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    @Cacheable(cacheNames = "PostWithComments")
    public List<Post> getPostsWithComments(int pageNumber, Sort.Direction sort) {
        List<Post> allPosts = postRepository.findAllPosts(
                PageRequest.of(pageNumber,PAGE_SIZE,
                        Sort.by(sort, "id")
                )
        );
        //wyciagniecie id z postow
        List<Long> ids = allPosts.stream()
                .map(Post::getId) //.map(post -> post.getId())
                .collect(Collectors.toList());
        // na podstawie id pobrac komentarze przez repository komentarzy
        List<Comment> comments = commentRepository.findAllByPostIdIn(ids);
        allPosts.forEach(post -> post.setComment(extractComments(comments,post.getId())));

        return allPosts;
    }

    private List<Comment> extractComments(List<Comment> comments, long id) {
        // przefiltrowane komentarze ktore naleza do tego posta
        return comments.stream()
                .filter(comment -> comment.getPostId() == id)
                .collect(Collectors.toList());
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional                                                  // do jednej transakcji zapytania hibernate z metody
    @CachePut(cacheNames = "SinglePost",key = "#result.id")         // adnotacja do cachowania edycji
    public Post editPost(Post post) {
        // zabezpieczenie przed brakiem id w post i aktualizacja tylko wybranych pól
        Post postEdited = postRepository.findById(post.getId()).orElseThrow();
        postEdited.setTitle(post.getTitle());
        postEdited.setContent(post.getContent());

        return postEdited;
        //return postRepository.save(post);
    }

    @CacheEvict(cacheNames = "SinglePost")                          // automatyczne usuwanie z cache'a przy usuwaniu
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    // usuwanie calosci cascha
    @CacheEvict(cacheNames = "PostWithComments")
    public void clearPostsWithComments(){

    }

}
