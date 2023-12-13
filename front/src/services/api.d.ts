export interface APIResponse {
  data: Object;
}

export interface ResponseError {
  respCode: string;
  respMsg: string;
  respBody: string;
}
