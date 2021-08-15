public class Article {
    public static final String URL ="https://vnexpress.net/rss/the-thao.rss";
    public static final String TITLE_TAG = "title";
    public static final String DESC_TAG = "description";
    public static final String PUB_DATE_TAG = "pubDate";
    public static final String LINK_TAG = "link";
    public static final String ITEM_TAG = "item";
    private String title;
    private String description;
    private String pubDate;
    private String link;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}