@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();

        String email = (String) attributes.get("email");

        User existing = userRepository.findByEmail(email).orElseGet(User::new);
        existing.setEmail(email);
        existing.setName((String) attributes.get("name"));
        existing.setPicture((String) attributes.get("picture"));
        userRepository.save(existing);

        return user;
    }
}