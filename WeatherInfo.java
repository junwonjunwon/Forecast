package teamproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.InputSource;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class WeatherInfo {
    public static Map<String, String> getNextMorningWeather() {
        Map<String, String> weatherInfo = new HashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        Calendar todayDate = Calendar.getInstance();

        String xml = HttpRequest.request("post", "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4127351000", "");
        InputSource is = new InputSource(new StringReader(xml));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(is);
        } catch (Exception e) {
            doc = null;
            System.out.println(e.getLocalizedMessage());
            System.exit(0);
        }
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("data");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                if (Integer.parseInt(getTagValue("hour", element)) < 8 && Integer.parseInt(getTagValue("hour", element)) > 5) {
                    todayDate.add(Calendar.DATE, Integer.parseInt(getTagValue("day", element)));
                    String resultDate = dateFormat.format(todayDate.getTime());
                    double rainAmount = Double.parseDouble(getTagValue("r06", element)) + Double.parseDouble(getTagValue("s06", element));

                    weatherInfo.put("location", getTagValue("category", doc.getDocumentElement()));
                    weatherInfo.put("hour", getTagValue("hour", element));
                    weatherInfo.put("date", resultDate);
                    weatherInfo.put("temperature", getTagValue("temp", element));
                    weatherInfo.put("rainPercent", getTagValue("pop", element));
                    weatherInfo.put("cloud", getTagValue("sky", element));
                    weatherInfo.put("rain", getTagValue("pty", element));
                    weatherInfo.put("korWeather", getTagValue("wfKor", element));
                    weatherInfo.put("windSpeed", getTagValue("ws", element));
                    weatherInfo.put("humidity", getTagValue("reh", element));
                    weatherInfo.put("rainAmount", Double.toString(rainAmount));
                    break;
                }
            }
        }
        return weatherInfo;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }
}