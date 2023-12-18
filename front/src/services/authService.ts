import http from './httpService';
import axios from 'axios';

interface LoginReqType {
  email: string;
  password: string;
}

interface ResponseErrorType {
  respCode: string;
  respMsg: string;
  respBody: string;
}

interface EmailCodeVerifyType {
  email: string;
  code: string;
}
/**
 * @function
 * 로고 이미지 가져오기
 */
export async function getLogoImg(): Promise<any> {
  try {
    const response = await axios.get('/cat?json=true', {
      withCredentials: true,
    });
    return `https://cataas.com/${response.data}`;
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
}
/**
 * @function
 * 로그아웃
 */
export const apiLogout = async (data: null): Promise<any> => {
  try {
    return http.get('/auth/logout');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
/**
 *  @function
 *  로그인
 */
export const apiLogin = async (data: LoginReqType): Promise<any> => {
  try {
    return await http.post('/auth/login', data);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
/**
 * @function
 * access token 재요청
 */
export const apiGetAccessToken = async (data: null): Promise<any> => {
  try {
    return await http.get('/auth/reissue');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 이메일 인증 요청
 */
export const apiPostCreditsEmail = async (email: string): Promise<any> => {
  try {
    return await http.post('/api/v1/mails/send-certification', {
      email: email,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      console.log('이메일 인증 요청 실패:::', e.response);
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 이메일 인증 코드 입력
 */
export const apiGetEmailCodeVerify = async (
  data: EmailCodeVerifyType,
): Promise<any> => {
  try {
    return await http.get('/api/v1/mails/verify', {
      params: {
        email: data.email,
        certificationNumber: data.code,
      },
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
