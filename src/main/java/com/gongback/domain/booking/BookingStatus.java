package com.gongback.domain.booking;

public enum BookingStatus {
    PENDING,    // 결제 대기
    CONFIRMED,  // 확정
    CANCELLED,  // 취소
    COMPLETED,  // 수강 완료
    NOSHOW      // 노쇼
}
