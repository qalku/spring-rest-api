package pl.mw.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import pl.mw.restapi.controller.dto.PostDto;
import pl.mw.restapi.model.Post;
import pl.mw.restapi.service.PostService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static pl.mw.restapi.controller.PostDtoMapper.mapToPostDtos;

@RestController
@RequiredArgsConstructor
@Tag(name = "Posty - PostController", description = "Lista postów API.")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Pobranie wszystkich postow bez komentarzy")
    @GetMapping("/posts")
    public List<PostDto> getPosts(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page!=null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort !=null ? sort : Sort.Direction.ASC;
        return mapToPostDtos(postService.getPosts(pageNumber, sortDirection));
    }

    // Z HATHEOAS:
    @GetMapping("/posts/hth")
    public CollectionModel<EntityModel<PostDto>> getPostsHTH(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page!=null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort !=null ? sort : Sort.Direction.ASC;

        // do CollectionModel > extrakcja, stream i przemapowanie listy na EntityModel
        List<EntityModel<PostDto>> posts = PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber, sortDirection)).stream()
                .map(postDto -> EntityModel.of(postDto))
                .toList();

        //return PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber, sortDirection));
        return CollectionModel.of(posts,
                linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostsHTH(page,sort)).withSelfRel(), //nonstatic: WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getSinglePost(id)).withSelfRel(),
                linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostsHTH(page,sort)).withRel("posts")
                );
    }

    // posty z komentarzami
    @Operation(summary = "Pobranie wszystkich postow wraz z komentarzami")
    @GetMapping("/posts/comments")
    @SecurityRequirement(name = "bearerAuth")
    public List<Post> getPostsWithComment(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page!=null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort !=null ? sort : Sort.Direction.ASC;
        return postService.getPostsWithComments(pageNumber, sortDirection);
    }

    @Operation(summary = "Pobranie pojedynczego posta wg ID")
    @GetMapping("/posts/{id}")
    // public Post getSinglePost(@PathVariable long id) {   //<< przed HATOAS
    public EntityModel<Post> getSinglePost(@PathVariable long id) {      //<< Po HATOAS
        // BEZ LINK HATHEOAS:
        //return postService.getSinglePost(id);

        // Z HATHEOAS:
        return EntityModel.of(postService.getSinglePost(id),
                linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getSinglePost(id)).withSelfRel(), //nonstatic: WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getSinglePost(id)).withSelfRel(),
                linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getSinglePost(id)).withRel("posts"),
                linkTo(PostController.class).slash("posts").slash(id).slash("info").withRel("info")
        );
    }

    @Operation(summary = "Dodawanie nowego posta")
    @PostMapping("posts")
    public Post addPost(@RequestBody Post post){
        return postService.addPost(post);
    }

    @Operation(summary = "Edycja posta wg ID")
    @PutMapping("/posts")
    public Post editPost(@RequestBody Post post){
        return postService.editPost(post);
    }

    @Operation(summary = "Usunięcie posta wg ID")
    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable long id){
        postService.deletePost(id);
    }
}
