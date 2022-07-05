import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class Exercise2 {
    private static final String URL_POST = "https://jsonplaceholder.typicode.com/users/";
    private static final String URL_COMMENT = "https://jsonplaceholder.typicode.com/posts/";

    public static void comments(Integer userId) throws IOException, InterruptedException {
        List<UserPost> users = sendGet(userId, URL_POST + userId + "/posts");
        Integer i=  users.stream().mapToInt(u -> u.getId())
                .max().getAsInt();
        List<UserPost> userPosts = sendGet( i, URL_COMMENT + i + "/comments");

        String path = "user-" + userId + "-post-" + i + "-comments.json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(userPosts);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(path))){
            printWriter.write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static List<UserPost> sendGet(Integer userId, String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type userListType = new TypeToken<ArrayList<UserPost>>(){}.getType();
        List<UserPost> users = gson.fromJson(response.body(), userListType);
        return users;
    }
}

class UserPost{
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "postId=" + postId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}