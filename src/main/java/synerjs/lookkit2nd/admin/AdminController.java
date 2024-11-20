package synerjs.lookkit2nd.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.common.dto.InquiryUserDTO;
import synerjs.lookkit2nd.common.response.BaseResponse;
import synerjs.lookkit2nd.inquiry.service.InquiryService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final InquiryService is;
//
    @GetMapping("/dashboard")
    public ResponseEntity dashboard(){
        List<InquiryUserDTO> inquiryList = is.getAllInquiries();
        inquiryList.forEach(inquiry -> System.out.println("Inquiry: " + inquiry));
        return    ResponseEntity.status(200).body(inquiryList);
    }
//
//
//    @GetMapping("/inqury")
//    public void inqury(Model model){
//    }
//
//    @GetMapping("/orderStatus")
//    public void orderStatus(Model model){
//        List<UserOrderDTO> orderList =  os.orderAllListWithUserUuid();
//        orderList.forEach(order -> System.out.println("order: " + order));
//        model.addAttribute("orderList",orderList);
//    }
//
//
//    @PostMapping("/updateOrderStatus")
//    @ResponseBody
//    public Map<String, Object> updateOrderStatus(@RequestBody Map<String, String> requestData){
//        Map<String, Object> response = new HashMap<>();
//        try {
//            int orderId = Integer.parseInt(requestData.get("orderId"));
//            String orderStatus = requestData.get("orderStatus");
//
//            System.out.println("주문번호:" + orderId);
//            System.out.println("주문상태:" + orderStatus);
//
//            int result = os.updateOrderStatus(orderId, orderStatus);
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
//
//    @GetMapping("/answer/{id}")
//    public String answer(@PathVariable int id,Model model){
//        System.out.println("전달받은 문의번호"+id);
//        model.addAttribute("inquiry" , is.findByInquiryId(id));
//        InquiryAnswerVO answer =is.getAnswer(id);
//        if (answer != null) {
//            model.addAttribute("answer", answer);
//        } else {
//            model.addAttribute("answer",null);
//        }
//        return "/admin/inquiryAnswer";
//    }
//
//    @PostMapping("/answer")
//    public String submitAnswer(InquiryAnswerVO answerVO){
//        int result =   is.insertInquiryAnswer(answerVO);
//        System.out.println( result>0 ? "답변완료":"답변에러");
//        return "redirect:/admin/dashboard";
//    }
//

}
