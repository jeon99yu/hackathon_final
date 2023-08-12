package co_2.suggest_project.Controller;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.Model.PostResponseDTO;
import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Repository.PostRepository;
import co_2.suggest_project.Repository.UserRepository;
import co_2.suggest_project.Service.UserService;
import co_2.suggest_project.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class UserController {
    private UserRepository userRepository;
    private UserService userService;
    private JwtUtil jwtUtil;
    private PostRepository postRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, PostRepository postRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/test")
    public UserDTO test(){
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName("서빈");
        userDTO.setPassword("1234");
        userDTO.setEmail("111@naver.com");
        userDTO.setRole("회원");
        System.out.println(userDTO);
        return userDTO;
        //userService.insertUser(userDTO);
    }

    @PostMapping("/test")
    public void testInsertUser() {
        UserDTO userDTO = new UserDTO();
     //   userDTO.setUserId(1L);     UserId는 필요없음.
        userDTO.setNickName("서빈");
        userDTO.setPassword("1234");
        userDTO.setEmail("1111@naver.com");
        userDTO.setRole("회원");
        userService.insertUser(userDTO);
    }

    @PostMapping("/users/password")  // 비밀번호 찾기
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO.ChangePasswordRequest request) {
        boolean result = userService.updatePassword(request.getEmail(), request.getCurrentPassword(), request.getNewPassword(), request.getConfirmNewPassword());
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PatchMapping("/users/password")  // 비밀번호 변경
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody UserDTO.ChangePasswordRequest request) {
        // JWT에서 사용자 정보 추출
        String email = jwtUtil.getEmailFromToken(token);
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
        UserEntity user = userOptional.get();
        // 기존 비밀번호 일치하는지 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("현재 비밀번호가 일치하지 않습니다.");
        }
        // 새로운 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
        // 비밀번호 업데이트
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 성공적으로 변경되었습니다.");
    }

    @DeleteMapping("/users/mypage")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        // JWT 검증
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 토큰입니다.");
        }
        // JWT에서 사용자 정보 추출
        String email = jwtUtil.getEmailFromToken(token);

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        List<PostResponseDTO> posts = postRepository.findAllByUser(userOptional.get());
        postRepository.deleteAll(posts);
        userRepository.delete(userOptional.get()); // 사용자 정보 삭제
        // 댓글은 없음 아직

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 성공적으로 삭제되었음을 응답합니다.
    }

    @GetMapping("/users/{userId}")        // test : users/13으로
    public ResponseEntity<UserEntity> getUserInfo(@PathVariable Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
