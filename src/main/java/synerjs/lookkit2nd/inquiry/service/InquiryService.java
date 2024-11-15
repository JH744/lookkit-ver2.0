package synerjs.lookkit2nd.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        List<InquiryImageDTO> inquiryImages = imgRepository.findInquiryByIdWithImage(inquiryId)
                .stream()
                .map(img -> new InquiryImageDTO(img.getInquiryId(), img.getInquiryImageId(), img.getImagePath()))
                .collect(Collectors.toList());

        return InquiryResponseDTO.fromEntity(inquiry, inquiryImages); // 이미지 포함하여 DTO 생성
    }

    // 문의하기
    public InquiryResponseDTO createInquiry(InquiryRequestDTO request, List<MultipartFile> files) throws IOException {
        // Inquiry Entity 변환 및 저장
        Inquiry inquiry = request.toEntity();
        inqRepository.save(inquiry);


        List<InquiryImageDTO> imagesToSave = new ArrayList<>();

        // 이미지가 있는 경우, 이미지 저장 및 정보 DB 저장
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String savedFileName = FileUtil.saveFile(file);
                    String imagePath = "/upload/inquiry/" + savedFileName;

                    // 이미지 정보 DB 저장
                    InquiryImage saveImg = InquiryImage.builder()
                            .inquiryId(inquiry.getInquiryId())
                            .imagePath(imagePath)
                            .build();
                    imgRepository.save(saveImg);

                    // DTO 리스트에 추가
                    imagesToSave.add(InquiryImageDTO.fromEntity(saveImg));
                }
            }
        }
        return InquiryResponseDTO.fromEntity(inquiry, imagesToSave);
    }
}
