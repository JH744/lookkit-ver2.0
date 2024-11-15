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

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<InquiryResponseDTO> createInquiry(@RequestPart("request") InquiryRequestDTO request,
                                                          @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        InquiryResponseDTO response = inquiryService.createInquiry(request, files);
        return new BaseResponse<>(response);
    }





}
