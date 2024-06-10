import http from '@/services/httpService';
import axios from 'axios';

/**
 * @function
 * 유저 - 내 정보 조회
 */
export const apiGetMyInfo = async () => {
  try {
    return await http.get('/api/v1/member/me');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
}

/**
 * @function
 * 유저 - 알림
 */
export const apiGetAlarmList = async () => {
  try {
    return await http.get('/api/v1/alarm/list')
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
}

/**
 * @function
 * 유저 - 알림 상세
 */
export const apiGetAlarmRead = async (id: number) => {
  try {
    return await http.get(`/api/v1/alarm/${id}`)
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
}