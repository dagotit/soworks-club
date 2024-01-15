import http from '@/services/httpService';
import axios from 'axios';
import { isEmptyObj } from '@/utils/common';
import { FilterQueryParamType } from '@/hooks/useCalendar';

export const apiGetMonthCalendar = async (month: number | null) => {
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

export const apiGetClubList = async (query: FilterQueryParamType | {}) => {
  // 기본값
  const param: any = !isEmptyObj(query)
    ? query
    : {
        isAll: false,
        startDate: '',
        endDate: '',
        isAttendClub: false,
        isCreateClub: false,
      };

  console.log('param', param);
  try {
    return Promise;
    // return await http.get(`/api/v1/group-attend/list?all=${param.isAll}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
