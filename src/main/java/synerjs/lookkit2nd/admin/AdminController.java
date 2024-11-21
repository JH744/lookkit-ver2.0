package synerjs.lookkit2nd.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.dto.InquiryUserDTO;
import synerjs.lookkit2nd.common.response.BaseResponse;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;
import synerjs.lookkit2nd.inquiry.entity.InquiryAnswer;
import synerjs.lookkit2nd.inquiry.service.InquiryService;
import synerjs.lookkit2nd.order.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final InquiryService inquiryService;
    private final OrderService orderService;

    //문의 목록 조회
    @GetMapping("/dashboard")
    public ResponseEntity dashboard(){
        List<InquiryUserDTO> inquiryList = inquiryService.getAllInquiries();
        inquiryList.forEach(inquiry -> System.out.println("Inquiry: " + inquiry));
        return    ResponseEntity.status(200).body(inquiryList);
    }

//    // 주문 현황 조회
//    @GetMapping("/orders")
//    public ResponseEntity<List<UserOrderDTO>> getOrderStatus() {
//        List<UserOrderDTO> orderList = orderService.orderAllListWithUserUuid();
//        return ResponseEntity.ok(orderList); // HTTP 200
//    }
//
//
//    // 주문 상태 업데이트
//    @PutMapping("/orders/{orderId}/status")
//    public Map<String, Object> updateOrderStatus(
//            @PathVariable int orderId,
//            @RequestParam String orderStatus
//    ) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            int result = orderService.updateOrderStatus(orderId, orderStatus);
//            if (result > 0) {
//                response.put("success", true);
//                response.put("message", "주문 상태가 성공적으로 업데이트되었습니다.");
//            } else {
//                response.put("success", false);
//                response.put("message", "주문 상태 업데이트에 실패했습니다.");
//            }
//        } catch (Exception e) {
//            response.put("success", false);
//            response.put("message", "오류 발생: " + e.getMessage());
//        }
//        return response;
//    }


// 특정 문의 상세 조회
    @GetMapping("/inquiries/{id}")
    public ResponseEntity getInquiryDetails(@PathVariable long id) {
            InquiryUserDTO inquiryDetails = inquiryService.findInquiryById(id);
            return ResponseEntity.ok(inquiryDetails);
    }

    // 특정 문의 답변 조회
    @GetMapping("/inquiries/{id}/answer")
    public ResponseEntity<InquiryAnswer> getAnswerByInquiryId(@PathVariable Long id) {
        InquiryAnswer answer = inquiryService.getAnswerByInquiryId(id);
        return ResponseEntity.ok(answer);
    }

    // 문의에 답변 저장
    @PostMapping("/inquiries/{id}/answer")
    public ResponseEntity<InquiryAnswer> saveAnswer(@PathVariable Long id, @RequestBody InquiryAnswer answer) {

        // Inquiry 객체를 설정
        InquiryAnswer inquiryAnswer  = InquiryAnswer
                .builder()
                .answerContents(answer.getAnswerContents())
                .inquiry(inquiryService.findInquiryByInquiryId(id))
                .build();


        // Answer 저장
        InquiryAnswer savedAnswer = inquiryService.saveAnswer(inquiryAnswer,id);
        return ResponseEntity.ok(savedAnswer);
    }


}
