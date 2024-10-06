import {getClientMessageByErrorCode} from "./error-message-constants.js";

export function handleResponseBody(response) {
  return response.json().then(body => {
    if (body.ok && body.responseType === 'SUCCESS') {
      return body;
    }
    if (!body.ok && body.responseType === 'ERROR') {
      throw new Error(getClientMessageByErrorCode(body.data.errorCode));
    }
    throw new Error("An unknown error occurred");
  });
}
