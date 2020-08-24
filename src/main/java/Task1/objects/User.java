package Task1.objects;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

public class User<HM extends HashMap<String, String>> {

    String _id;
    HM name;

    public User(String _id, HM name) {
        this._id = _id;
        this.name = name;
    }

    public User(LinkedTreeMap userMap) {

    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return name.get("first") + " " + name.get("last");
    }

    @Override
    public String toString() {
        return this.getName() + " [" + _id + "]";
    }
}
