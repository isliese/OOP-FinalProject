package oopFinalProject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Payment extends JFrame {

    private JLabel instLabel; // 결제 지시 label 저장용 필드
    private Timer timer; // 타이머 필드
    private final String paymentMethod; // final 키워드 추가

    public Payment(String paymentMethod, int total) {
        this.paymentMethod = paymentMethod; // paymentMethod 초기화

        // 프레임 설정
        JFrame frame = new JFrame("HOLLYS 키오스크");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800); // 프레임 크기 설정

        // hollys_logao 원본 이미지 아이콘 불러오기
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/hollys_logo.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // 원하는 크기로 이미지 아이콘 조절
        ImageIcon hollysIcon = new ImageIcon(resizedImage);

        // 이미지 레이블 설정
        JLabel iconimageLabel = new JLabel(hollysIcon);

        // 폰트 설정
        Font totalFont = new Font("맑은 고딕", Font.BOLD, 15);

        // 결제 방법 및 금액 label 설정
        JLabel paymentLabel = new JLabel();
        String labelText = "<html><div style='text-align: center;'>" + paymentMethod + "를 선택하셨습니다.<br>결제 금액: " + total + "원</div></html>"; // 텍스트 중앙 정렬
        paymentLabel.setText(labelText);
        paymentLabel.setFont(totalFont);
        paymentLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 중앙 정렬

        // 결제 지시 label 초기 설정
        String instruction = "";
        if (paymentMethod.equals("카드 결제")) {
            instruction = "<html><div style='text-align: center;'>5초 뒤 자동으로 결제가 완료됩니다.<br>카드를 삽입해 주세요.</html>";
        } else if (paymentMethod.equals("상품권 결제")) {
            instruction = "<html><div style='text-align: center;'>5초 뒤 자동으로 결제가 완료됩니다.<br>아래 바코드 인식기에 상품권 바코드를 인식해주세요.</html>";
        }
        instLabel = new JLabel(instruction, SwingConstants.CENTER);
        Font instructionFont = new Font("맑은 고딕", Font.BOLD, 15);
        instLabel.setFont(instructionFont);

        // 패널 생성 및 설정
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(iconimageLabel, BorderLayout.NORTH);
        // 결제 방법과 결제 금액을 포함한 패널을 생성
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(paymentLabel, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(instLabel, BorderLayout.SOUTH);

        // 타이머 설정 - 5초 후에 다른 페이지로 전환
        timer = new Timer(1000, new ActionListener() {
            int remainingTime = 5; // 초기 남은 시간 설정

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                if (remainingTime > 0) {
                    updateInstruction(remainingTime);
                } else {
                    timer.stop(); // 타이머 멈춤
                    frame.dispose(); // 현재 프레임 닫기
                    if (paymentMethod.equals("카드 결제")) {
                        new paymentSuccessCard(); // PaymentSuccessCard 클래스에서 적절한 페이지로 전환하는 코드
                    } else if (paymentMethod.equals("상품권 결제")) {
                        new paymentSuccessQR(); // PaymentSuccessQR 클래스에서 적절한 페이지로 전환하는 코드
                    }
                }
            }
        });
        timer.start();

        // 프레임에 패널 추가
        frame.add(panel);

        // 프레임 보이기
        frame.setVisible(true);
    }

    // 결제 지시 label 업데이트 메서드
    private void updateInstruction(int remainingTime) {
        String instruction = "";
        if (remainingTime > 0) {
            if (instLabel != null) {
                if (paymentMethod.equals("카드 결제")) {
                    instruction = "<html><div style='text-align: center;'>" + remainingTime + "초 뒤 자동으로 결제가 완료됩니다.<br>카드를 삽입해 주세요.</html>";
                } else if (paymentMethod.equals("상품권 결제")) {
                    instruction = "<html><div style='text-align: center;'>" + remainingTime + "초 뒤 자동으로 결제가 완료됩니다.<br>아래 바코드 인식기에 상품권 바코드를 인식해주세요.</html>";
                }
                instLabel.setText(instruction);
            }
        }
    }
}
