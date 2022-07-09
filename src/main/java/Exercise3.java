import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class Exercise3 {
    private static final String URL_TODO = "https://jsonplaceholder.typicode.com/users/";

    public static void toDo(Integer userId) throws IOException, InterruptedException {
        List<UserId> userIds = sendGet(userId, URL_TODO + userId + "/todos");
        Predicate<UserId> predicate = (u) -> u.getCompleted()==false;
        userIds.stream().filter(u -> predicate.test(u))
                .forEach(userId1 -> System.out.println(userId1.getTitle()));
    }

    public static List<UserId> sendGet(Integer userId, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type userListType = new TypeToken<ArrayList<UserId>>(){}.getType();
        List<UserId> users = gson.fromJson(response.body(), userListType);
        return users;
    }
}

class UserId{
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "UserId{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
