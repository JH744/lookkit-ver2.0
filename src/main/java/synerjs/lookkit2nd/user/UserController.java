package synerjs.lookkit2nd.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


//    @GetMapping("/{id}")
//    public UserDTO getUser(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//    @PostMapping
//    public UserDTO createUser(@RequestBody UserDTO userDTO) {
//        return userService.createUser(userDTO);
//    }
}