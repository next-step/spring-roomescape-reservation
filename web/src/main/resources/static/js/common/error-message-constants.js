export const errorCode = {
  NOT_DEFINED: 'NOT_DEFINED',
  SERVER_ERROR: 'SERVER_ERROR',
  R404: 'R404',
  R405: 'R405',
  R406: 'R406',
  RT404: 'RT404',
  RT405: 'RT405',
  RT406: 'RT406',
  RT407: 'RT407',
  TH404: 'TH404',
  TH406: 'TH406',
}

export const errorMessages = {
  [errorCode.NOT_DEFINED]: "알 수 없는 오류가 발생했습니다.",
  [errorCode.SERVER_ERROR]: "서버 내부 오류가 발생했습니다. 나중에 다시 시도해주세요.",
  [errorCode.R404]: "예약을 찾을 수 없습니다.",
  [errorCode.R405]: "중복된 예약이 있습니다.",
  [errorCode.R406]: "이미 취소된 예약입니다.",
  [errorCode.RT404]: "예약 시간을 찾을 수 없습니다.",
  [errorCode.RT405]: "중복된 예약 시간이 있습니다.",
  [errorCode.RT406]: "이미 사용 중인 예약 시간입니다.",
  [errorCode.RT407]: "예약 가능 시간 범위를 벗어났습니다.",
  [errorCode.TH404]: "테마를 찾을 수 없습니다.",
  [errorCode.TH406]: "이미 사용 중인 테마입니다."
};

export function getClientMessageByErrorCode(errorCode) {
  return errorMessages[errorCode] || "알 수 없는 오류가 발생했습니다.";
}
