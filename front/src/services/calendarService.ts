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
      `/api/v1/calendar/list?month=${query.month}&year=${query.year}`,
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
  console.log('query:', query);
  const stYear = DateTime.fromFormat(query.startDate, 'yyyy-MM-dd').year;
  const stMonth = DateTime.fromFormat(query.startDate, 'yyyy-MM-dd').month;
  const endYear = DateTime.fromFormat(query.endDate, 'yyyy-MM-dd').year;
  const endMonth = DateTime.fromFormat(query.endDate, 'yyyy-MM-dd').month;
  const joinOnly = query.isAttendClub ? 'Y' : 'N';
  const makeOnly = query.isCreateClub ? 'Y' : 'N';
  const statusNotDone = query.statusClub ? 'Y' : 'N';

  // TODO: 멤버 id는 회원가입할 때.. 담으면.. 새로고침하면ㅇ ㅓ떻게 확인하지?
  try {
    return await http.get(
      `/api/v1/groups/group-list?stYear=${stYear}&stMonth=${stMonth}&endYear=${endYear}&endMonth=${endMonth}&memberId=1&joinOnly=${joinOnly}&makeOnly=${makeOnly}&statusNotDone=${statusNotDone}`,
    );
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
