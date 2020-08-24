package Task1.objects;

public class CatFact< E extends User> {

    String _id;
    String text;
    String type;
    Integer upvotes;
    Integer userUpvoted;
    E user;

    public CatFact(String _id, String text, String type, Integer upvotes, Integer userUpvoted, E user) {
        this._id = _id;
        this.text = text;
        this.type = type;
        this.upvotes = upvotes;
        this.userUpvoted = userUpvoted;
        this.user = user;
    }

    public String getId() {
        return _id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public Integer getUserUpvoted() {
        return userUpvoted;
    }

    public String getUser() {
        return user.toString();
    }

    @Override
    public String toString() {
        return "----------" + '\n' +
                "id: " + _id + '\n' +
                "text: " + text + '\n' +
                "type: " + type + '\n' +
                "upVotes: " + upvotes + '\n' +
                "userUpvotes: " + userUpvoted + '\n' +
                "author: " + user + '\n' +
                "----------";
    }
}
