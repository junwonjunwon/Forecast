package teamproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import java.util.Map;

public class WeatherWindow extends JFrame implements ActionListener {
    private JPanel container = new JPanel();
    private JPanel btnPanel = new JPanel();

    private JLabel location = new JLabel();
    private JLabel date = new JLabel();
    private JLabel hour = new JLabel();
    private JLabel temperature = new JLabel();
    private JLabel skyExplain = new JLabel();
    private JLabel rainPercent = new JLabel();
    private JLabel rainAmount = new JLabel();
    private JLabel humidity = new JLabel();
    private JLabel windSpeed = new JLabel();
    private JLabel outsidePercent = new JLabel();

    private JButton btnReload;
    private JButton btnExit;

    public WeatherWindow() {
        this.setLayout(new BorderLayout());

        container.setLayout(new GridLayout(10, 1, 5, 5));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));

        location.setFont(CustomFont.getDefaultFont());
        date.setFont(CustomFont.getDefaultFont());
        hour.setFont(CustomFont.getDefaultFont());
        temperature.setFont(CustomFont.getDefaultFont());
        skyExplain.setFont(CustomFont.getDefaultFont());
        rainPercent.setFont(CustomFont.getDefaultFont());
        rainAmount.setFont(CustomFont.getDefaultFont());
        humidity.setFont(CustomFont.getDefaultFont());
        windSpeed.setFont(CustomFont.getDefaultFont());
        outsidePercent.setFont(CustomFont.getDefaultFont());

        container.add(location);
        container.add(date);
        container.add(hour);
        container.add(temperature);
        container.add(skyExplain);
        container.add(rainPercent);
        container.add(rainAmount);
        container.add(humidity);
        container.add(windSpeed);
        container.add(outsidePercent);

        btnReload = new JButton("새로고침");
        btnExit = new JButton("닫기");

        btnReload.setFont(CustomFont.getDefaultFont());
        btnReload.setFont(CustomFont.getDefaultFont());

        btnReload.addActionListener(this);
        btnExit.addActionListener(this);

        btnPanel.add(btnReload);
        btnPanel.add(btnExit);

        this.add(container);
        this.add(btnPanel, BorderLayout.SOUTH);

        this.getWeather();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void getWeather() {
        Map<String, String> weatherInfo = WeatherInfo.getNextMorningWeather();

        String locationString;
        if (weatherInfo.get("location").equals("경기도 안산시단원구 와동")) {
            locationString = "경기도 안산시 단원구 와동";
        } else {
            String message[] = {"위치 정보에 문제가 발생했습니다.", "관리자 또는 개발자에게 문의해주세요"};
            new Alert(this, message);
            locationString = "N/A";
        }

        String[] dateString = weatherInfo.get("date").split("[^\\d]+");

        double probability;
        String holidayInfo = "";
        String response = Holiday.check(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(dateString[2]));
        if (response.equalsIgnoreCase("none")) {
            probability = this.getProbability(Double.parseDouble(weatherInfo.get("temperature")), Double.parseDouble(weatherInfo.get("rainPercent")));
        } else {
            probability = 0;
            holidayInfo = " (" + response + ")";
        }

        location.setText("위치: " + locationString);
        date.setText("날짜: " + weatherInfo.get("date"));
        hour.setText("시각: " + weatherInfo.get("hour") + "시");
        temperature.setText("기온: " + weatherInfo.get("temperature") + " °C");
        skyExplain.setText("날씨: " + weatherInfo.get("korWeather"));
        rainPercent.setText("강수확률: " + weatherInfo.get("rainPercent") + " %");
        rainAmount.setText("예상강수량: " + weatherInfo.get("rainAmount") + " mm");
        humidity.setText("습도: " + weatherInfo.get("humidity") + " %");
        windSpeed.setText("풍속: " + weatherInfo.get("windSpeed") + " m/s");
        outsidePercent.setText("야외점호 확률: " + probability + " %" + holidayInfo);

        this.pack();

        // System.out.println(weatherInfo.toString());
    }

    private double getProbability(double temperature, double rain) {
        return (double)Math.round(200 * (1 - Math.pow(rain / 100, 1.2)) * normalDistIntegration(0, temperature / 10) * 10) / 10;
    }

    private double normalDistIntegration(double start, double end) {
        if (end < 0) return 0;
        double sum = 0;
        int n = 100000;
        for (int i = 0; i < n; i++) {
            sum += normalDist(start + (end - start) * i / n, 0.8, 0) * (end - start) / n;
        }
        return sum;
    }

    private double normalDist(double x, double sigma, double mean) {
        return Math.exp(-1 * Math.pow(x - mean, 2) / (2 * sigma * sigma)) / (Math.sqrt(2 * Math.PI) * sigma);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "닫기") {
            System.exit(0);
        } else if (e.getActionCommand() == "새로고침") {
            this.getWeather();
        }
    }
}