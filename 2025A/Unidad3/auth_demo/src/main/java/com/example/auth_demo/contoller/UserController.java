@RestController
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> getUserInfo(OAuth2AuthenticationToken authentication) {
        OAuth2User user = authentication.getPrincipal();
        return user.getAttributes();
    }

    @GetMapping("/")
    public String home() {
        return "Página pública";
    }
}