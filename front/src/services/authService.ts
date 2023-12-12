import http from './httpService';
import axios, { AxiosResponse } from 'axios';

interface LoginReqType {
  email: string;
  password: string;
}

export async function getLogoImg(): Promise<any> {
  try {
    const response = await axios.get('/cat?json=true', {
      withCredentials: true,
    });
    console.log(response);
    return `https://cataas.com/${response.data}`;
  } catch (e) {
    return '';
  }
}

export const apiLogout = async (): Promise<any> => {
  try {
    return http.get('/logout');
  } catch (e) {
    throw e;
  }
};

export const apiLogin = async (data: LoginReqType): Promise<any> => {
  try {
    return await http.post('/login', data);
  } catch (e) {
    throw e;
  }
};

export const apiGetAccessToken = async (data: null): Promise<any> => {
  try {
    return await http.get('/reissue');
  } catch (e) {
    throw e;
  }
};
