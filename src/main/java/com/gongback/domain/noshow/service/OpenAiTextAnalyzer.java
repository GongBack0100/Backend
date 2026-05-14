package com.gongback.domain.noshow.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiTextAnalyzer {

    private final RestClient openAiRestClient;
    private final ObjectMapper objectMapper;

    @Value("${openai.model}")
    private String model;

    private static final String SYSTEM_PROMPT = """
            당신은 한국어 클래스 예약 플랫폼의 노쇼 리스크 분석 전문가입니다.
            고객이 예약 시 남긴 메시지를 분석해 노쇼 가능성을 0~100 사이의 정수로 판단하세요.

            판단 기준:
            - 불확실한 표현 ("아마", "봐서", "일정에 따라", "갈 것 같아요", "될지 모르겠어요") → 높은 리스크
            - 타인 의존 표현 ("친구 일정", "같이 가는 분이", "데려올 사람이") → 높은 리스크
            - 강한 의지 표현 ("꼭", "반드시", "확실히", "기대돼요", "설레요") → 낮은 리스크
            - 구체적 준비 언급 ("준비물 챙겼어요", "일찍 갈게요") → 낮은 리스크
            - 메시지가 없거나 단순 인사 → 50 (중립)

            응답은 반드시 아래 JSON 형식만 출력하세요. 마크다운이나 추가 텍스트 없이:
            {"riskScore": 숫자, "summary": "한 문장 요약"}
            """;

    public TextAnalysisResult analyze(String notes) {
        if (notes == null || notes.isBlank()) {
            return new TextAnalysisResult(50, "예약 메시지 없음");
        }

        try {
            Map<String, Object> requestBody = Map.of(
                    "model", model,
                    "temperature", 0.2,
                    "messages", List.of(
                            Map.of("role", "system", "content", SYSTEM_PROMPT),
                            Map.of("role", "user", "content", "예약 메시지: " + notes)
                    )
            );

            String rawResponse = openAiRestClient.post()
                    .uri("/v1/chat/completions")
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            return parseResponse(rawResponse);

        } catch (Exception e) {
            log.error("OpenAI API 호출 실패: {}", e.getMessage());
            throw new RuntimeException("텍스트 분석 중 오류가 발생했습니다.", e);
        }
    }

    private TextAnalysisResult parseResponse(String rawResponse) throws Exception {
        ChatCompletionResponse response = objectMapper.readValue(rawResponse, ChatCompletionResponse.class);
        String content = response.choices().get(0).message().content().trim();

        // GPT가 JSON을 마크다운 블록에 감싸는 경우 대비
        if (content.startsWith("```")) {
            content = content.replaceAll("```json\\s*|```\\s*", "").trim();
        }

        GptAnalysisResult result = objectMapper.readValue(content, GptAnalysisResult.class);
        int clampedScore = Math.max(0, Math.min(100, result.riskScore()));
        return new TextAnalysisResult(clampedScore, result.summary());
    }

    public record TextAnalysisResult(int riskScore, String summary) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record ChatCompletionResponse(List<Choice> choices) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Choice(Message message) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Message(String content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record GptAnalysisResult(int riskScore, String summary) {}
}
