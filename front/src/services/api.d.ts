export interface APIResponse {
  data: Object;
}

export interface ResponseSuccess {
  respCode: string;
  respMsg: string;
  respBody: APIResponse;
}

export interface ResponseError {
  respCode: string;
  respMsg: string;
  respBody: string;
}
