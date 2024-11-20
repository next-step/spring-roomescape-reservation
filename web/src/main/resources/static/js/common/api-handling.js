import {errorCode, getClientMessageByErrorCode} from "./error-message-constants.js";
import {httpStatus} from "./http-status-code.js";

const RESPONSE_TYPE_SUCCESS = 'SUCCESS';
const RESPONSE_TYPE_ERROR = 'ERROR';

function handleError(body) {
  if (body.statusCode === httpStatus.BAD_REQUEST) {
    if (body.data?.errorCode === errorCode.NOT_DEFINED) {
      throw new Error('잘못된 입력입니다.');
    }
  }

  throw new Error(getClientMessageByErrorCode(body.data.errorCode));
}

export function handleResponseBody(response) {
  return response.json()
      .then(body => {
        if (body.responseType === RESPONSE_TYPE_SUCCESS) {
          return body;
        }
        if (body.responseType === RESPONSE_TYPE_ERROR) {
          return handleError(body);
        }
        throw new Error("An unknown error occurred");
      });

  throw new Error("An unknown error occurred");
}
