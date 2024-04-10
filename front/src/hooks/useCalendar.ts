import {
  apiGetAttendance,
  apiGetClubList,
  apiGetMonthCalendar,
} from '@/services/calendarService';
import { useMutation, useQueries } from '@tanstack/react-query';
import { DateTime } from 'luxon';

export interface FilterQueryParamType {
  isAll: boolean;
  startDate: any;
  endDate: any;
  isAttendClub: boolean;
  isCreateClub: boolean;
  statusClub: boolean;
  findDate?: any;
}

export interface CalendarParamType {
  stYear: number;
  endYear: number;
  stMonth: number;
  endMonth: number;
  joinOnly: boolean;
  makeOnly: boolean;
  statusNotDone: boolean;
}

/**
 * @function
 * 출석 api
 */
export const useGetAttendance = () => {
  return useMutation({
    mutationKey: ['get-attendance'],
    mutationFn: apiGetAttendance,
  });
};

/**
 * @function
 * 1. 캘린더 api
 * 2. 모임 리스트 api
 */
export const useGetCalendarQuerys = (
  calendarQuery: CalendarParamType,
  clubListQuery: FilterQueryParamType,
) => {
  return useQueries({
    queries: [
      {
        queryKey: ['get-month-calendar', calendarQuery],
        queryFn: async () =>  await apiGetMonthCalendar(calendarQuery),
        staleTime: 0,
        // retry: false, // 실패시 재시도 횟수
      },
      {
        queryKey: ['get-club-list', clubListQuery, calendarQuery],
        queryFn: async () => await apiGetClubList(clubListQuery),
        staleTime: 0,
        // retry: false,
      },
    ],
    combine: (results) => {
      return {
        calendar: results[0].data,
        isLoading: results[0].isLoading,
        clubList: results[1].data
      }
    },
  });
};
