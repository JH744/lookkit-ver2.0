package synerjs.lookkit2nd.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import synerjs.lookkit2nd.common.dto.InquiryUserDTO;
import synerjs.lookkit2nd.common.exception.BaseException;
import synerjs.lookkit2nd.common.response.BaseResponseStatus;
import synerjs.lookkit2nd.common.util.FileUtil;
import synerjs.lookkit2nd.inquiry.dto.InquiryImageDTO;
import synerjs.lookkit2nd.inquiry.dto.InquiryRequestDTO;
import synerjs.lookkit2nd.inquiry.dto.InquiryResponseDTO;
import synerjs.lookkit2nd.inquiry.entity.Inquiry;
import synerjs.lookkit2nd.inquiry.entity.InquiryAnswer;
import synerjs.lookkit2nd.inquiry.entity.InquiryImage;
import synerjs.lookkit2nd.inquiry.repository.InquiryAnswerRepository;
import synerjs.lookkit2nd.inquiry.repository.InquiryImageRepository;
import synerjs.lookkit2nd.inquiry.repository.InquiryRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inqRepository;
    private final InquiryImageRepository imgRepository;
    private final InquiryAnswerRepository inquiryAnswerRepository;

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

    }
//
public List<InquiryUserDTO> getAllInquiries() {
    List<Object[]> results =  inqRepository.getAllInquiriesWithUserUuid();
    return results.stream()
            .map(row -> new InquiryUserDTO(
                    ((Number) row[0]).longValue(), // inquiryId
                    (String) row[1],              // userUuid
                    (String) row[2],              // inquiryTitle
                    (String) row[3],              // inquiryContents
                    ((Timestamp) row[4]).toLocalDateTime(), // inquiryCreatedAt
                    row[5].toString()
            ))
            .toList();
}



    public Inquiry findInquiryByInquiryId(Long inquiryId) {
        return inqRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 문의를 찾을 수 없습니다. ID: " + inquiryId));
    }

    @Transactional(readOnly = true)
    public InquiryUserDTO findInquiryById(Long inquiryId) {
        Optional<Object[]> rawData = inqRepository.findInquiryDetailsById(inquiryId);

        if (rawData.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 데이터를 찾을 수 없습니다. inquiryId: " + inquiryId);
        }

        Object[] row = rawData.get();

        // 2차원 배열인 경우 1차원 배열로 변환
        if (row.length == 1 && row[0] instanceof Object[]) {
            row = (Object[]) row[0];
        }

        return mapToInquiryUserDTO(row);
    }

    private InquiryUserDTO mapToInquiryUserDTO(Object[] row) {
        if (row == null || row.length < 6) {
            throw new IllegalArgumentException("결과 데이터가 잘못되었습니다. 예상 필드가 부족합니다.");
        }

        return InquiryUserDTO.builder()
                .inquiryId(((Number) row[0]).longValue())         // INQUIRY_ID
                .userUuid(row[1].toString())                      // USER_UUID
                .inquiryTitle(row[2].toString())                  // INQUIRY_TITLE
                .inquiryContents(row[3].toString())               // INQUIRY_CONTENTS
                .inquiryCreatedAt(((Timestamp) row[4]).toLocalDateTime()) // INQUIRY_CREATED_AT
                .answerState(row[5].toString())                   // ANSWER_STATE
                .build();
    }


    // 특정 문의 ID로 답변 데이터 조회
    public InquiryAnswer getAnswerByInquiryId(Long inquiryId) {
        return inquiryAnswerRepository.findByInquiry_InquiryId(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 문의에 대한 답변을 찾을 수 없습니다. ID: " + inquiryId));
    }
    // 답변하기
    @Transactional
    public InquiryAnswer saveAnswer(InquiryAnswer answer, long inquiryId) {
         inqRepository.updateAnswerStateToCompleted(inquiryId);
        return inquiryAnswerRepository.save(answer);
    }





}

