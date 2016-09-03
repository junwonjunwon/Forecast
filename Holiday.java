package teamproject;

public class Holiday {
    public static String check(int year, int month, int day) {
        String response = HttpRequest.request("GET", "http://dimilife.kr/holiday", "year="+year+"&month="+month+"&day="+day);
        System.out.println(response);
        return response;
    }
}
