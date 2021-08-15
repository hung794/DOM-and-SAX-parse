import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class DOM_Parser {
    public static void main(String[] args) {
        ArrayList<Article> articleArrayList = getListArticleByDOM();
        if (DataBase.createTable()) {
            System.out.println("Create table articles success!");
        } else System.err.println("Create table articles failed!");
        if (DataBase.insertIntoTable(articleArrayList)) {
            System.out.println("Insert data into table articles success!");
        } else System.err.println("Insert data into table articles failed!");
    }

    public static ArrayList<Article> getListArticleByDOM() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        ArrayList<Article> articleArrayList = new ArrayList<>();
        {
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(Article.URL);
                Element element = document.getDocumentElement();
                NodeList list = element.getElementsByTagName(Article.ITEM_TAG);
                for (int i = 0; i < list.getLength(); i++) {
                    Article article = new Article();
                    Node node = list.item(i);
                    NodeList nodeList = node.getChildNodes();
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node childNode = nodeList.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            switch (childNode.getNodeName()) {
                                case Article.TITLE_TAG:
                                    article.setTitle(childNode.getTextContent());
                                    break;
                                case Article.DESC_TAG:
                                    article.setDescription(childNode.getTextContent());
                                    break;
                                case Article.PUB_DATE_TAG:
                                    article.setPubDate(childNode.getTextContent());
                                    break;
                                case Article.LINK_TAG:
                                    article.setLink(childNode.getTextContent());
                                    break;
                            }
                        }
                    }
                    articleArrayList.add(article);
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        return articleArrayList;
    }
}