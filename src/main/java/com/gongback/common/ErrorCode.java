package com.gongback.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Auth
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // Class
    CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "클래스를 찾을 수 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),
    SCHEDULE_FULL(HttpStatus.BAD_REQUEST, "해당 일정이 마감되었습니다."),

    // Booking
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    ALREADY_BOOKED(HttpStatus.CONFLICT, "이미 예약된 일정입니다."),
    CANNOT_CANCEL(HttpStatus.BAD_REQUEST, "취소할 수 없는 예약입니다."),

    // Favorite
    ALREADY_FAVORITED(HttpStatus.CONFLICT, "이미 찜한 클래스입니다."),
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "찜 목록에 없는 클래스입니다."),

    // Payment
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
    PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "결제에 실패했습니다."),

    // Vacancy
    VACANCY_NOT_FOUND(HttpStatus.NOT_FOUND, "빈자리 정보를 찾을 수 없습니다."),

    // Notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),

    // General
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
