import {
  apiGetAttendance,
  apiGetClubList,
  apiGetMonthCalendar,
} from '@/services/calendarService';
import { useMutation, useQuery } from '@tanstack/react-query';
import { isEmptyObj } from '@/utils/common';

export interface FilterQueryParamType {
  isAll: boolean;
  startDate: any;
  endDate: any;
  isAttendClub: boolean;
  isCreateClub: boolean;
  statusClub: boolean;
}
export const useGetMonthCalendar = (query: { month: number; year: number }) => {
  return useQuery({
    queryKey: ['get-month-calendar', query],
    queryFn: () => apiGetMonthCalendar(query),
    retry: 0,
    // staleTime: Infinity,
  });
};

export const useGetAttendance = () => {
  return useMutation({
    mutationKey: ['get-attendance'],
    mutationFn: apiGetAttendance,
  });
};

export const useGetClubList = (query: FilterQueryParamType) => {
  return useQuery({
    queryKey: ['get-club-list', query],
    queryFn: () => apiGetClubList(query),
    retry: 0,
  });
};
