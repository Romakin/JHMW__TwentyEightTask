package Task1.objects;

public class CatFact<S extends String, I extends Integer, E extends User> {

    S id;
    S text;
    S type;
    I upvotes;
    I userUpvoted;
    E user;

    public CatFact(S _id, S text, S type, I upvotes, I userUpvoted, E user) {
        this.id = _id;
        this.text = text;
        this.type = type;
        this.upvotes = upvotes;
        this.userUpvoted = userUpvoted;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public I getUpvotes() {
        return upvotes;
    }

    public I getUserUpvoted() {
        return userUpvoted;
    }

    public String getUser() {
        return user.toString();
    }

    @Override
    public String toString() {
        return "----------" + '\n' +
                "id: " + id + '\n' +
                "text: " + text + '\n' +
                "type: " + type + '\n' +
                "upVotes: " + upvotes + '\n' +
                "userUpvotes: " + userUpvoted + '\n' +
                "author: " + user + '\n' +
                "----------";
    }
}
