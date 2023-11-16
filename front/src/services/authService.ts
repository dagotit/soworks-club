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

export function logout(): Promise<any> {
  return http.get('/user/logout');
}

export const apiLogin = async (data: LoginReqType): Promise<any> => {
  try {
    return await http.post('/login', data);
  } catch (e) {
    return null;
  }
};
