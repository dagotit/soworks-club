import http from './httpService';
import { AxiosResponse } from 'axios';

interface LoginReqType {
  email: string;
  password: string;
}

export function logout(): Promise<any> {
  return http.get('/user/logout');
}

export const apiLogin = async (data: LoginReqType): Promise<any> => {
  try {
    return await http.post('/signup', data);
  } catch (e) {
    return null;
  }
};
