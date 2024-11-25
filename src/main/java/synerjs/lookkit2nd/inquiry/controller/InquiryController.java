package synerjs.lookkit2nd.inquiry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import synerjs.lookkit2nd.common.response.BaseResponse;
import synerjs.lookkit2nd.inquiry.dto.InquiryRequestDTO;
import synerjs.lookkit2nd.inquiry.dto.InquiryResponseDTO;
import synerjs.lookkit2nd.inquiry.service.InquiryService;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping("/user/{userId}")
    public BaseResponse<List<InquiryResponseDTO>> getUserInquiries(@PathVariable Long userId) {
        List<InquiryResponseDTO> response = inquiryService.getUserInquiries(userId);
        return new BaseResponse<>(response);
    }

    @GetMapping("/{inquiryId}")
    public BaseResponse<InquiryResponseDTO> getInquiryDetail(@PathVariable Long inquiryId) {
        InquiryResponseDTO response = inquiryService.getInquiryDetail(inquiryId);
        return new BaseResponse<>(response);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<InquiryResponseDTO> createInquiry(@RequestBody InquiryRequestDTO request) throws IOException {
        InquiryResponseDTO response = inquiryService.createInquiry(request);
        return new BaseResponse<>(response);
    }

    @DeleteMapping("/{inquiryId}")
    public BaseResponse<Long> deleteInquiry(@PathVariable Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return new BaseResponse<>(inquiryId);
    }



}
