import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/daily-news?useUnicode=true&characterEncoding=utf-8";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PWD = "";
    private static Connection cnn;

    public static Connection getConnection() throws SQLException {
        if (cnn == null || cnn.isClosed()) {
            cnn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PWD);
        }
        return cnn;
    }

    public static boolean createTable() {
        boolean created = false;
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE");
        sqlBuilder.append(" IF NOT EXISTS ");
        sqlBuilder.append("articles");
        sqlBuilder.append("(title VARCHAR(200), ");
        sqlBuilder.append("description TEXT, ");
        sqlBuilder.append("pubDate VARCHAR(50), ");
        sqlBuilder.append("link VARCHAR(100))");
        try {
            Statement statement = getConnection().createStatement();
            statement.execute(sqlBuilder.toString());
            created = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return created;
    }

    public static boolean insertIntoTable(ArrayList<Article> list) {
        boolean inserted = false;
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("INSERT INTO articles");
            sqlBuilder.append("(title, description, pubDate, link)");
            sqlBuilder.append(" VALUES");
            sqlBuilder.append(" (?, ?, ?, ?)");
            for (Article article : list) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(sqlBuilder.toString());
                preparedStatement.setString(1, article.getTitle());
                preparedStatement.setString(2, article.getDescription());
                preparedStatement.setString(3, article.getPubDate());
                preparedStatement.setString(4, article.getLink());
                preparedStatement.execute();
                inserted = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return inserted;
    }
}