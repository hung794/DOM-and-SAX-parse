import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;

public class SAX_Parser {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userHandler = new UserHandler();
            saxParser.parse(Article.URL, userHandler);
            ArrayList<Article> articleArrayList = userHandler.getListArticle();
            if (DataBase.createTable()) {
                System.out.println("Create table articles success!");
            } else System.err.println("Create table articles failed!");
            if (DataBase.insertIntoTable(articleArrayList)) {
                System.out.println("Insert data into table articles success!");
            } else System.err.println("Insert data into table articles failed!");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    static class UserHandler extends DefaultHandler {
        private final ArrayList<Article> articleArrayList = new ArrayList<>();
        private Article article;
        private boolean isItem;
        private boolean isTitle;
        private boolean isDesc;
        private boolean isPubDate;
        private boolean isLink;

        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch (qName) {
                case Article.ITEM_TAG:
                    article = new Article();
                    isItem = true;
                    break;
                case Article.TITLE_TAG:
                    isTitle = true;
                    break;
                case Article.DESC_TAG:
                    isDesc = true;
                    break;
                case Article.PUB_DATE_TAG:
                    isPubDate = true;
                    break;
                case Article.LINK_TAG:
                    isLink = true;
                    break;
            }
        }

        public void endElement(String uri, String localName, String qName) {
            switch (qName) {
                case Article.ITEM_TAG:
                    articleArrayList.add(article);
                    break;
                case Article.TITLE_TAG:
                    isTitle = false;
                    break;
                case Article.DESC_TAG:
                    isDesc = false;
                    break;
                case Article.PUB_DATE_TAG:
                    isPubDate = false;
                    break;
                case Article.LINK_TAG:
                    isLink = false;
                    break;
            }
        }

        public void characters(char[] ch, int start, int length) {
            if (isItem) {
                String content = new String(ch, start, length).trim();
                if (isTitle) {
                    article.setTitle(content);
                } else if (isDesc) {
                    article.setDescription(content);
                } else if (isPubDate) {
                    article.setPubDate(content);
                } else if (isLink) {
                    article.setLink(content);
                }
            }
        }

        public ArrayList<Article> getListArticle() {
            return articleArrayList;
        }
    }
}