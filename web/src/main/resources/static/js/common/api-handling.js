import {getClientMessageByErrorCode} from "./error-message-constants.js";

export function handleResponseBody(response) {
  console.log(response);

  return response.json()
      .then(body => {
        if (body.responseType === 'SUCCESS') {
          return body;
        }
        if (body.responseType === 'ERROR') {
          throw new Error(getClientMessageByErrorCode(body.data.errorCode));
        }
        throw new Error("An unknown error occurred");
      });

  throw new Error("An unknown error occurred");
}
