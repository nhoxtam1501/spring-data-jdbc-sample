package bootiful.jdbc;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

public class Customer implements Serializable {
    @Id
    private Integer id;
    private String name;

    private Profile profile;

    private Set<Order> orders;

    public Customer(Integer id, String name, Profile profile, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.orders = orders;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profile=" + profile +
                ", orders=" + orders +
                '}';
    }
}
