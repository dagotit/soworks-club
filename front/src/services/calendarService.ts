import http from '@/services/httpService';
import axios from 'axios';
import { CalendarParamType, FilterQueryParamType } from '@/hooks/useCalendar';
import { DateTime } from 'luxon';

/**
 * @function
 * 달력
 */
export const apiGetMonthCalendar = async (query: CalendarParamType) => {
  console.log('달력:', query);
  const joinOnly = query.joinOnly ? 'Y' : 'N';
  const makeOnly = query.makeOnly ? 'Y' : 'N';
  const statusNotDone = query.statusNotDone ? 'Y' : 'N';

  try {
    return await http.get(
      `/api/v1/calendar/list?stMonth=${query.stMonth}&stYear=${query.stYear}&endYear=${query.endYear}&endMonth=${query.endMonth}&joinOnly=${joinOnly}&makeOnly=${makeOnly}&statusNotDone=${statusNotDone}`,
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
    return await http.get('/api/v1/attendance/attend');
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
  console.log('리스트:', query);
  const stYear = DateTime.fromFormat(query.startDate, 'yyyy-MM-dd').year;
  const stMonth = DateTime.fromFormat(query.startDate, 'yyyy-MM-dd').month;
  const endYear = DateTime.fromFormat(query.endDate, 'yyyy-MM-dd').year;
  const endMonth = DateTime.fromFormat(query.endDate, 'yyyy-MM-dd').month;
  const joinOnly = query.isAttendClub ? 'Y' : 'N';
  const makeOnly = query.isCreateClub ? 'Y' : 'N';
  const statusNotDone = query.statusClub ? 'Y' : 'N';

  let url = `/api/v1/groups/group-list?stYear=${stYear}&stMonth=${stMonth}&endYear=${endYear}&endMonth=${endMonth}&joinOnly=${joinOnly}&makeOnly=${makeOnly}&statusNotDone=${statusNotDone}`;

  if (query.findDate) {
    url += `&findDate=${DateTime.fromFormat(query.findDate, 'yyyy-MM-dd').day}`;
  }
  try {
    return await http.get(url);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
