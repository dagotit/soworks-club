import http from './httpService';

export function logout() {
  return http.get('/user/logout');
}

export function apiLogin(data: any) {
  return http.post('/user/login', data).then(({ data }) => data.data);
}
