package com.in28minutes.rest.webservices.restful_web_services.user;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository,  PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    //http://localhost:8080/users
    // 데이터와 링크로 응답을 생성하려면
    // EntityModel, WebMvcLinkBuilder가 필요함.
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        // User user = repository.findById(id).get() 가 안되는 이유가
        // 아까는 findByOne으로 우리가 지정한 메소드로 stream으로 하나만 필터링하게 했는데
        // 이거는 기본 함수로 여러개 갖고오는거니깐,
        if(user.isEmpty()){
            throw new UserNotFoundException("id:" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }


    @DeleteMapping("/jpa/users/{id}")
    public void delete(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id:" + id);
        }
        return user.get().getPosts();
    }

    // hateoas 방식은 아님! _links 를 포함해야하니까
    // json 본문이 없는 상태에서 Location 헤더 반환함. 즉 리소스 URI를 알려주는 방식 (xml)
    // 그냥 json 응답만 추가하려면 .created(location).body(savedUser); 이렇게 하면 됨
    // 근데 그렇게 했다고하더라도 hateoas방식은 아님
    @PostMapping("/jpa/users")
    public ResponseEntity<EntityModel> createUser(@Valid @RequestBody  User user){
        User savedUser = userRepository.save(user);
        // /users/4 => /users/{id}, user.getId

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(savedUser.getId()).toUri();
        // 새로 생성된 사용자 ID를 URL로 만듬 (새롭게 URL 생성하는거야!)

        return ResponseEntity.created(location).build();
        //Location 헤더만 포함된 응답을 생성 그리고 그 URL로 리다렉하는거지!
        // 이게 포스트요청이니까 만약 id가 4였다면 글을 작성한 순간 만들어진 http://localhost:8080/jpa/users/4로 간다구!
    }


    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }





}
