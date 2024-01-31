export interface APIResponse {
  data: Object | undefined | Array<any>;
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
