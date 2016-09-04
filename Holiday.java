package teamproject;

public class Holiday {
    public static String check(int year, int month, int day) {
        String response = HttpRequest.request("GET", "http://dimilife.kr/holiday", "year="+year+"&month="+month+"&day="+day);
        if (response.equalsIgnoreCase("none")) {
            int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            String[] dayName = {"일", "월", "화", "수", "목", "금", "토"};
            int dayCount = (year - 1900) * 365;
            dayCount += Math.floor((year - 1900) / 4) - Math.floor((year - 1900) / 100) + Math.floor((year - 1600) / 400);
            if (((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) && month <= 2)
                dayCount -= 1;
            for (int i = 0; i < month - 1; i++)
                dayCount += daysOfMonth[i];
            dayCount += day;
            dayCount %= 7;
            if (dayCount == 0 || dayCount == 6)
                response = dayName[dayCount];
        }
        return response;
    }
}
