package synerjs.lookkit2nd.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import synerjs.lookkit2nd.common.exception.BaseException;
import synerjs.lookkit2nd.common.response.BaseResponseStatus;
import synerjs.lookkit2nd.common.util.FileUtil;
import synerjs.lookkit2nd.inquiry.dto.InquiryImageDTO;
import synerjs.lookkit2nd.inquiry.dto.InquiryRequestDTO;
import synerjs.lookkit2nd.inquiry.dto.InquiryResponseDTO;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;
import synerjs.lookkit2nd.inquiry.entity.InquiryImage;
import synerjs.lookkit2nd.inquiry.repository.InquiryImageRepository;
import synerjs.lookkit2nd.inquiry.repository.InquiryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inqRepository;
    private final InquiryImageRepository imgRepository;


    // 사용자의 문의 리스트
    @Transactional(readOnly = true)
    public List<InquiryResponseDTO> getUserInquiries(Long userId) {
        return inqRepository.findByUserIdOrderByInquiryCreatedAtDesc(userId)
                .stream()
                .map(inquiry -> InquiryResponseDTO.fromEntity(inquiry, Collections.emptyList())) // 이미지 없이 DTO 생성
                .collect(Collectors.toList());
    }

    // 특정 문의의 상세 정보 가져오기
    @Transactional(readOnly = true)
    public InquiryResponseDTO getInquiryDetail(Long inquiryId) {
        Inquiry inquiry = inqRepository.findById(inquiryId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INQUIRY_NOT_FOUNT));

        List<InquiryImageDTO> inquiryImages = imgRepository.findInquiryByIdWithImage(inquiryId)
                .stream()
                .map(img -> new InquiryImageDTO(img.getInquiryId(), img.getInquiryImageId(), img.getImagePath()))
                .collect(Collectors.toList());

        return InquiryResponseDTO.fromEntity(inquiry, inquiryImages); // 이미지 포함하여 DTO 생성
    }

    // 문의하기
    public InquiryResponseDTO createInquiry(InquiryRequestDTO request) throws IOException {

        // Inquiry Entity 변환 및 저장
        Inquiry inquiry = Inquiry.builder()
                .userId(request.getUserId())
                .inquiryTitle(request.getInquiryTitle())
                .inquiryContents(request.getInquiryContents())
                .build();
        Inquiry savedInquiry =  inqRepository.save(inquiry);

        List<InquiryImageDTO> imagesToSave = new ArrayList<>();

        // 이미지 정보 저장 (매핑 없이 inquiryId를 통해 이미지와 연결)
        if (request.getImageUrls() != null) {
            for (String imageUrl : request.getImageUrls()) {
                InquiryImage image = InquiryImage.builder()
                        .inquiryId(savedInquiry.getInquiryId())
                        .imagePath(imageUrl)
                        .build();
                imgRepository.save(image);
                imagesToSave.add(InquiryImageDTO.fromEntity(image));
            }
        }
        return InquiryResponseDTO.fromEntity(inquiry, imagesToSave);
    }

    public void deleteInquiry(Long inquiryId) {
        inqRepository.findById(inquiryId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INQUIRY_NOT_FOUNT));
        inqRepository.deleteById(inquiryId);
    }
}
