import http from '@/services/httpService';
import axios from 'axios';
import { isEmptyObj } from '@/utils/common';
import { FilterQueryParamType } from '@/hooks/useCalendar';
import { DateTime } from 'luxon';

/**
 * @function
 * 달력
 */
export const apiGetMonthCalendar = async (query: {
  month: number;
  year: number;
}) => {
  try {
    return await http.get(
      `/api/v1/groups/group-list?month=${query.month}&year=${query.year}`,
    );
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 출석체크
 */
export const apiGetAttendance = async (data: null) => {
  try {
    return http.get('/api/v1/attendance/attend');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 모임리스트 ( 필터 )
 */
export const apiGetClubList = async (query: FilterQueryParamType) => {
  const year = DateTime.fromFormat(query.startDate, 'yyyy-MM-dd').year;
  const month = DateTime.fromFormat(query.startDate, 'yyyy-MM-dd').month;

  try {
    return await http.get(
      `/api/v1/groups/group-list?month=${month}&year=${year}`,
    );
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
