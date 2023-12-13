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

export const apiLogout = async (data: null): Promise<any> => {
  try {
    return http.get('/logout');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

export const apiLogin = async (data: LoginReqType): Promise<any> => {
  try {
    return await http.post('/login', data);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

export const apiGetAccessToken = async (data: null): Promise<any> => {
  try {
    return await http.get('/reissue');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
