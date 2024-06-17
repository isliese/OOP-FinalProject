package oopFinalProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Menu {
    private JFrame frame; // 메인 프레임
    private JTable table; // 주문 내역을 표시할 테이블
    private DefaultTableModel model; // 테이블의 모델 : 데이터를 관리
    private JLabel totalLabel; // 총 금액을 표시할 totalLabel
    private int total = 0; // 총 금액을 저장하는 변수

    // 커피 메뉴 9개: 커피 메뉴명과 가격을 저장하는 배열
    private String[] coffeeNames = {
        "블랙아리아 아메리카노", "블랙아리아 딥라떼", "돌체라떼",
        "디카페인 아메리카노", "디카페인 카페라떼", "디카페인 바닐라딜라이트",
        "콜드브루 딜라이트", "콜드브루 라떼", "콜드 브루"
    };
    private int[] coffeePrices = {
        5000, 5000, 6200, 4900, 5600, 6600,
        6500, 5700, 5100
    };
    // 논커피 메뉴 9개: 논커피 메뉴명과 가격  
    private String[] nonCoffeeNames = {"리얼 수박 생과일 주스","오렌지 자몽 싱싱 주스","토마토 통통 주스", "제주 말차 할라치노", "민트 초코칩 할라치노", "딸기 치즈케익 할라치노", "유자몽 스파클링", "복숭아 자두 스파클링", "청포도 스파클링" };
    private int[] nonCoffeePrices = {6500, 6500, 6500, 6300, 6300, 6300 ,5800, 5800, 5800};
    
    // 푸드 메뉴 9개: 푸드 메뉴명과 가격 
    private String[] foodNames= {"블루베리 치즈케이크", "봉주르 크림브륄레", "푸딩에 반하나 쏘스윗박스", "트리플 베리 요거트", "할리베어 초코홀릭 케이크", "자이언트 페퍼로니 프레즐", "콰트로 치즈 프레즐","더블 햄치즈 크루아상","B.E.L.T 호밀 샌드위치"};
    private int[] foodPrices= {6500, 3300, 6500, 6300, 39000, 4900, 4900, 6500, 6500};	
    private JTextArea detailsArea; // 주문 상세 내역을 표시할 텍스트 Area

    
    // 생성자: 프레임과 구성 요소들을 초기화
    public Menu() {
        frame = new JFrame("HOLLYS 키오스크");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 탭 패널 생성 & 커피 메뉴 패널 추가
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel coffeePanel = createMenuPanel(coffeeNames, coffeePrices, "coffee");
        JPanel nonCoffeePanel= createMenuPanel(nonCoffeeNames, nonCoffeePrices, "noncoffee");
        JPanel foodPanel= createMenuPanel(foodNames, foodPrices, "food"); 
       
        tabbedPane.add("커피", coffeePanel);
        tabbedPane.add("논커피", nonCoffeePanel);       
        tabbedPane.add("푸드", foodPanel); 
        
        model = new DefaultTableModel();
        model.addColumn("메뉴");
        model.addColumn("가격");
        model.addColumn("수량");
        model.addColumn("합계");

        table = new JTable(model);
        JScrollPane scrollPaneForTable = new JScrollPane(table);
        scrollPaneForTable.setPreferredSize(new Dimension(400, 200));
        frame.add(scrollPaneForTable, BorderLayout.CENTER);

        // 주문 상세 내역 패널 생성
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsArea = new JTextArea(10, 30);
        detailsArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        frame.add(detailsPanel, BorderLayout.EAST);

        // 총 금액 및 결제 버튼 패널 생성
        JPanel southPanel = new JPanel(new BorderLayout());

        // 홈 화면 버튼 패널 (왼쪽 정렬)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton cancelButton = new JButton("홈 화면");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HomePage();
                frame.setVisible(false);
            }
        });
        
        leftPanel.add(cancelButton);

        // 총 금액 및 결제 버튼 패널 (가운데 정렬)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalLabel = new JLabel("총 금액: 0원");
        centerPanel.add(totalLabel);

        JButton payButton = new JButton("결제하기");
        payButton.addActionListener(e -> showPaymentDialog());
        centerPanel.add(payButton);

        southPanel.add(leftPanel, BorderLayout.WEST);
        southPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(southPanel, BorderLayout.SOUTH);

        // 메뉴 탭 패널을 스크롤 패널에 추가 (짤리는 문제 발생하기 때문)
        JScrollPane scrollPane = new JScrollPane(tabbedPane);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        frame.add(scrollPane, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    
    // 메뉴 패널
    private JPanel createMenuPanel(String[] names, int[] prices, String imagePrefix) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        int itemsPerRow = 3; // 한 행에 표시할 메뉴 개수 (3개)
        int index = 0; // 메뉴 인덱스

        // 메뉴 아이템을 패널에 추가
        for (int row = 0; index < names.length; row++) {
            gbc.gridy = row; // 현재 행 설정 
            for (int col = 0; col < itemsPerRow && index < names.length; col++) {
                gbc.gridx = col; // 현재 열 설정 
                JPanel itemPanel = createMenuItem(names[index], prices[index], imagePrefix + index);
                panel.add(itemPanel, gbc); // 패널에 메뉴 아이템 추가 
                index++; // 다음 메뉴 아이템으로 이동
            }
        }
        return panel;
    }

    
 // 개별 메뉴 아이템 패널 
    private JPanel createMenuItem(String name, int price, String imageName) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // 이미지 레이블 
        JLabel imageLabel = new JLabel();
        // 이미지 경로 
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + imageName + ".png"));
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(imageLabel, gbc);

        // 메뉴명 및 가격 레이블 
        JLabel nameLabel = new JLabel("<html><div style='text-align: center;'>" + name + "<br>" + price + "원</div></html>"); // 텍스트 중앙 정렬
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        // 수량 조절 및 담기 버튼 패널 
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton minusButton = new JButton("-");
        JTextField quantityField = new JTextField("0", 2);
        quantityField.setEditable(false); // 초기에는 0으로 설정하고 사용자가 조절 가능
        JButton plusButton = new JButton("+");
        JButton confirmButton = new JButton("담기");

        // - 버튼 액션 리스너
        minusButton.addActionListener(e -> {
            int count = Integer.parseInt(quantityField.getText());
            if (count > 0) {
                count--;
                quantityField.setText(String.valueOf(count));
            }
        });

        // + 버튼 액션 리스너 
        plusButton.addActionListener(e -> {
            int count = Integer.parseInt(quantityField.getText());
            count++;
            quantityField.setText(String.valueOf(count));
        });
        
        
        // 담기 버튼 액션 리스너
        confirmButton.addActionListener(e -> {
            int count = Integer.parseInt(quantityField.getText());
            if (count > 0) {
                // 주문 테이블에 데이터 추가 또는 업데이트
                boolean found = false;
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(name)) {
                        int currentQuantity = (int) model.getValueAt(i, 2);
                        int newQuantity = currentQuantity + count;
                        int newTotal = price * newQuantity;
                        model.setValueAt(newQuantity, i, 2); // 수량 업데이트
                        model.setValueAt(newTotal, i, 3); // 총 합계 업데이트
                        found = true;
                        break;
                    }
                }
                
                
                // 주문 테이블에 없는 경우 새로운 행 추가
                if (!found) {
                    int itemSum = price * count;
                    model.addRow(new Object[]{name, price, count, itemSum});
                }

                // 주문 상세 내역에 추가
                int itemSum = price * count;
                detailsArea.append("[" + name + "]   " + count + "개   총 " + itemSum + "원\n");

                // 총 금액 계산 및 업데이트
                total += itemSum;
                totalLabel.setText("총 금액: " + total + "원");

                // 수량 필드 초기화
                quantityField.setText("0");
            }
        });


        
        // 수량 조절 및 담기 버튼 패널
        controlPanel.add(minusButton);
        controlPanel.add(quantityField);
        controlPanel.add(plusButton);
        controlPanel.add(confirmButton);

        gbc.gridy = 2;
        panel.add(controlPanel, gbc);

        return panel;
    }


    
    // 결제창 표시 메서드 : 카드 결제, 상품권 결제, 취소하기 
    private void showPaymentDialog() {
        String[] paymentOptions = {"카드 결제", "상품권 결제", "취소"};
        int choice = JOptionPane.showOptionDialog(
                frame, "총 " + total + " 원 입니다.\n결제 방식을 선택하세요", "결제", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, paymentOptions, paymentOptions[0]);

        // 결제 선택 처리
        if (choice == 0) {
            // 카드 결제 선택
        	JOptionPane.showMessageDialog(frame, "카드 결제를 선택하셨습니다. 결제 화면으로 넘어갑니다.");
        	// Payment.java 페이지로 전환
        	new Payment("카드 결제", total); 
            frame.setVisible(false); // 현재 창 숨기기

        } else if (choice == 1) {
            // 상품권 결제 선택
        	JOptionPane.showMessageDialog(frame, "상품권 결제를 선택하셨습니다. 결제 화면으로 넘어갑니다.");
        	// Payment.java 페이지로 전환
            new Payment("상품권 결제", total);
            frame.setVisible(false); // 현재 창 숨기기

        } else {
            // 결제 취소 선택
            JOptionPane.showMessageDialog(frame, "결제를 취소하셨습니다.");
        }
    }

    // ** 결제하기


    
    // 메인 Menu 메서드
    public static void main(String[] args) {
        new Menu();
    }
}

