export const errorMessages = {
    NOT_DEFINED: "알 수 없는 오류가 발생했습니다.",
    SERVER_ERROR: "서버 내부 오류가 발생했습니다. 나중에 다시 시도해주세요.",
    R404: "예약을 찾을 수 없습니다.",
    R405: "중복된 예약이 있습니다.",
    R406: "이미 취소된 예약입니다.",
    RT404: "예약 시간을 찾을 수 없습니다.",
    RT405: "중복된 예약 시간이 있습니다.",
    RT406: "이미 사용 중인 예약 시간입니다.",
    RT407: "예약 가능 시간 범위를 벗어났습니다.",
    TH404: "테마를 찾을 수 없습니다.",
    TH406: "이미 사용 중인 테마입니다."
};

export function getClientMessageByErrorCode(errorCode) {
    return errorMessages[errorCode] || "알 수 없는 오류가 발생했습니다.";
}
