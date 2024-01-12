import http from '@/services/httpService';
import axios from 'axios';

export const apiGetMonthCalendar = async (month: any) => {
  if (!month) {
    return Promise;
  }
  try {
    return await http.get(`/api/v1/group-attend/list?groupId=${month}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

export const apiGetAttendance = async (data: null) => {
  try {
    return http.get('/api/v1/attendance/attend');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
