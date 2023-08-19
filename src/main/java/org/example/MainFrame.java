package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainFrame extends JFrame {
    public static Log log = new Log();
    public static List<PairPoints> pairPoints = new ArrayList<>();
    private JTextArea outputArea;
    private JButton openButton;
    private JButton calculateButton;
    private JButton exportButton;
    private JLabel statusLabel;

    private JButton getLogButton;

    private SessionFactory sessionFactory;

    public MainFrame() {
        super("Distance Calculator");

        openButton = new JButton("Open XML");
        calculateButton = new JButton("Calculate distance");
        exportButton = new JButton("Export to Excel");
        statusLabel = new JLabel("Ready");
        getLogButton = new JButton("Get Logs from Db");

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(openButton, BorderLayout.WEST);
        panel.add(getLogButton, BorderLayout.AFTER_LAST_LINE);
        panel.add(calculateButton, BorderLayout.CENTER);
        panel.add(exportButton, BorderLayout.EAST);

        Container container = getContentPane();
        container.add(panel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(statusLabel, BorderLayout.SOUTH);

        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openXML();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });

        getLogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getLogFromDb();
            }
        });

        sessionFactory = createSessionFactory();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private SessionFactory createSessionFactory() {
        SessionFactory sessionFactory1 = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Log.class).buildSessionFactory();
        return sessionFactory1;
    }

    private void openXML() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            parseXML(file);
        }
    }

    private void parseXML(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList pairList = doc.getElementsByTagName("pair");

            for (int i = 0; i < pairList.getLength(); i++) {
                Node pairNode = pairList.item(i);

                if (pairNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pairElement = (Element) pairNode;

                    PairPoints pointPair = new PairPoints();

                    Element point1Element = (Element) pairElement.getElementsByTagName("point").item(0);
                    Element point2Element = (Element) pairElement.getElementsByTagName("point").item(1);

                    int firstX = Integer.parseInt(point1Element.getElementsByTagName("x").item(0).getTextContent());
                    int firstY = Integer.parseInt(point1Element.getElementsByTagName("y").item(0).getTextContent());
                    pointPair.setFirstX(firstX);
                    pointPair.setFirstY(firstY);
                    if (point1Element.getElementsByTagName("z").getLength() != 0) {
                        int firstZ = Integer.parseInt(point1Element.getElementsByTagName("z").item(0).getTextContent());
                        pointPair.setFirstZ(firstZ);
                    }

                    int secondX = Integer.parseInt(point2Element.getElementsByTagName("x").item(0).getTextContent());
                    int secondY = Integer.parseInt(point2Element.getElementsByTagName("y").item(0).getTextContent());
                    pointPair.setSecondX(secondX);
                    pointPair.setSecondY(secondY);
                    if (point2Element.getElementsByTagName("z").getLength() != 0) {
                        int secondZ = Integer.parseInt(point2Element.getElementsByTagName("z").item(0).getTextContent());
                        pointPair.setSecondZ(secondZ);
                    }
                    pointPair.setId(i);
                    pairPoints.add(pointPair);
                    log.setFileName(file.getName());


                }
            }
            outputArea.append("Points from file : " + file + "\n");
            for (PairPoints p: pairPoints) {
                outputArea.append(p + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Transactional
    private void processLog(PairPoints pairPoint) {
        log.setFirstX(pairPoint.getFirstX());
        log.setFirstY(pairPoint.getFirstY());
        log.setFirstZ(pairPoint.getFirstZ());
        log.setSecondX(pairPoint.getSecondX());
        log.setSecondY(pairPoint.getSecondY());
        log.setSecondZ(pairPoint.getSecondZ());
        log.setDistance(pairPoint.getDistance());
        log.setPointId(pairPoint.getId());
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        System.out.println(pairPoint);
        System.out.println(log);
        session.save(log);
        System.out.println(pairPoint);
        session.getTransaction().commit();

        session.close();
    }


    public void logDistance(PairPoints pairPoint) {
        Date start = new Date();
        if (pairPoint.getFirstZ() == 0) {
            calculateDistanceTwo(pairPoint);
        } else {
            calculateDistanceThree(pairPoint);
        }
        Date end = new Date();
        log.setStart(start);
        log.setEnd(end);
        processLog(pairPoint);
        outputArea.append("Point ID: " + pairPoint.getId() + " X1: " + pairPoint.getFirstX() + " Y1: " + pairPoint.getFirstY() + " Z1: " + pairPoint.getFirstZ() + " X2: " + pairPoint.getSecondX() + " Y2: " + pairPoint.getSecondY() + " Z2: " + pairPoint.getSecondZ() + " Distance: " + pairPoint.getDistance() + "\n");
    }

    public void calculateDistanceTwo(PairPoints pairPoint){
        double distance = 0.0;
        distance = Math.sqrt(Math.pow(pairPoint.getFirstX() - pairPoint.getSecondX(), 2) + Math.pow(pairPoint.getFirstY() - pairPoint.getSecondY(), 2));
        pairPoint.setDistance(distance);
        log.setMethod("method for calculating in two dimensions");
    }

    public void calculateDistanceThree(PairPoints pairPoint){
        double distance = 0.0;
        distance = Math.sqrt(Math.pow(pairPoint.getFirstX() - pairPoint.getSecondX(), 2) + Math.pow(pairPoint.getFirstY() - pairPoint.getSecondY(), 2) + Math.pow(pairPoint.getFirstZ() - pairPoint.getSecondZ(), 2));
        pairPoint.setDistance(distance);
        log.setMethod("method for calculating in three dimensions");
    }

    private void calculate() {
        outputArea.setText("");
        List<PairPoints> points = new ArrayList<>(pairPoints);
        for (PairPoints point : points) {
            logDistance(point);
        }
    }
    @Transactional
    private void getLogFromDb() {
        outputArea.setText("");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Log> logList = session.createQuery("from Log").getResultList();
        System.out.println(logList);

        for (Log l : logList) {
            outputArea.append(String.valueOf(l));
            System.out.println(l);
        }
        session.getTransaction().commit();

        session.close();
    }


    @Transactional
    private void exportToExcel() {
        Session session = sessionFactory.openSession();

        // Получение списка всех объектов Points
        Query<Log> query = session.createQuery("from Log", Log.class);
        List<Log> logs = query.list();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Logs");

        // Заголовки столбцов
        String[] columns = {"ID", "File Name", "Point ID", "Distance", "First X", "First Y",
                "Second X", "Second Y", "First Z", "Second Z" , "Start", "End", "Method"};

        // Создание заголовков в Excel
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Заполнение данными
        int rowNum = 1;
        for (Log log : logs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(log.getId());
            row.createCell(1).setCellValue(log.getFileName());
            row.createCell(2).setCellValue(log.getPointId());
            row.createCell(3).setCellValue(log.getDistance());
            row.createCell(4).setCellValue(log.getFirstX());
            row.createCell(5).setCellValue(log.getFirstY());
            row.createCell(6).setCellValue(log.getSecondX());
            row.createCell(7).setCellValue(log.getSecondY());
            row.createCell(8).setCellValue(log.getFirstZ());
            row.createCell(9).setCellValue(log.getSecondZ());
            row.createCell(10).setCellValue(log.getStart().toString());
            row.createCell(11).setCellValue(log.getEnd().toString());
            row.createCell(12).setCellValue(log.getMethod());
        }

        // Автоподбор ширины столбцов
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Сохранение файла
        try {
            FileOutputStream fileOut = new FileOutputStream("points.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Файл успешно экспортирован в Excel!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        session.close();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new org.example.MainFrame();
            }
        });
    }

}