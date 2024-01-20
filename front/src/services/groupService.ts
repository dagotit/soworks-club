import http from '@/services/httpService';
import axios from 'axios';

/**
 * @function
 * 모임 상세
 */
export const apiGetGroupInfo = async (id: number) => {
  try {
    return http.get(`/api/v1/groups/info?groupId=${id}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
