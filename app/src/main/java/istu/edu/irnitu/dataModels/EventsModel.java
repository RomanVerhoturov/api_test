package istu.edu.irnitu.dataModels;


public class EventsModel {

    String name;
    String description;
    String date;
    String url;

    public EventsModel(String name, String description, String date, String url) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
