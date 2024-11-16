// 예약 상태 상수
export const RESERVATION_STATUS = {
  CONFIRMED: 'CONFIRMED',
  CANCELED: 'CANCELED',
};

// 예약 상태별 표시 텍스트
export const RESERVATION_STATUS_DISPLAY = {
  [RESERVATION_STATUS.CONFIRMED]: '확정됨',
  [RESERVATION_STATUS.CANCELED]: '취소됨',
};

// 예약 상태별 스타일 클래스 (부트스트랩 색상 활용)
export const RESERVATION_STATUS_STYLE = {
  [RESERVATION_STATUS.CONFIRMED]: 'text-primary',
  [RESERVATION_STATUS.CANCELED]: 'text-danger',
};

// 상태 표시 텍스트를 가져오는 유틸리티 함수
export const getReservationStatusText = (status) => {
  return RESERVATION_STATUS_DISPLAY[status] || '알 수 없음';
};

// 상태별 스타일 클래스를 가져오는 유틸리티 함수
export const getReservationStatusTextStyle = (status) => {
  return RESERVATION_STATUS_STYLE[status] || '';
}