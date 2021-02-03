package sh.platform.example.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private RestTemplate template;

    private String url;

    @Autowired
    public UserService(@Value("${user.url}") String url) {
        this.url = url;
        this.template = new RestTemplate();
    }

    public List<User> findAll() {
        ResponseEntity<User[]> response = template.getForEntity(url, User[].class);
        return Arrays.asList(response.getBody());
    }

    public void save(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        template.postForObject(url, request, User.class);
    }

    public Optional<User> findById(String id) {
        User user = template.getForObject(url + "/" + id, User.class);
        return Optional.ofNullable(user);
    }

    public void delete(User user) {
        template.delete(url + "/" + user.getId());
    }
}
