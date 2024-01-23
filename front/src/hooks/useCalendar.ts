import {
  apiGetAttendance,
  apiGetClubList,
  apiGetMonthCalendar,
} from '@/services/calendarService';
import { useMutation, useQueries } from '@tanstack/react-query';

export interface FilterQueryParamType {
  isAll: boolean;
  startDate: any;
  endDate: any;
  isAttendClub: boolean;
  isCreateClub: boolean;
  statusClub: boolean;
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
  calendarQuery: { month: number; year: number },
  clubListQuery: FilterQueryParamType,
) => {
  console.log();
  return useQueries({
    queries: [
      {
        queryKey: ['get-month-calendar', calendarQuery],
        queryFn: () => apiGetMonthCalendar(calendarQuery),
        staleTime: 0,
        // retry: false,
      },
      {
        queryKey: ['get-club-list', clubListQuery, calendarQuery],
        queryFn: () => apiGetClubList(clubListQuery),
        staleTime: 0,
        // retry: false,
      },
    ],
  });
};
