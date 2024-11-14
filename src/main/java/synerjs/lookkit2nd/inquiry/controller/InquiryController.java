package synerjs.lookkit2nd.inquiry.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping("/{userId}")
    public BaseResponse<List<InquiryResponseDTO>> getUserInquiries(@PathVariable Long userId) {
        List<InquiryResponseDTO> response = inquiryService.getUserInquiries(userId);
        return new BaseResponse<>(response);
    }

    @GetMapping("/{userId}/{inquiryId}")
    public BaseResponse<InquiryResponseDTO> getInquiryDetail(@PathVariable Long userId, @PathVariable Long inquiryId) {
        InquiryResponseDTO response = inquiryService.getInquiryDetail(userId);
        return new BaseResponse<>(response);
    }

    @PostMapping
    public BaseResponse<InquiryResponseDTO> createInquiry(@RequestBody InquiryRequestDTO request,
                                                          @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        InquiryResponseDTO response = inquiryService.createInquiry(request, files);
        return new BaseResponse<>(response);
    }





}
