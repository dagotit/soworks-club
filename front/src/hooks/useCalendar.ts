import {
  apiGetAttendance,
  apiGetClubList,
  apiGetMonthCalendar,
} from '@/services/calendarService';
import { useMutation, useQuery } from '@tanstack/react-query';

export interface FilterQueryParamType {
  isAll: boolean;
  startDate: any;
  endDate: any;
  isAttendClub: boolean;
  isCreateClub: boolean;
}
export const useGetMonthCalendar = (month: number | null) => {
  return useQuery({
    queryKey: ['get-month-calendar', month],
    queryFn: () => apiGetMonthCalendar(month),
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

export const useGetClubList = (query: FilterQueryParamType | {}) => {
  return useQuery({
    queryKey: ['get-club-list', query],
    queryFn: () => apiGetClubList(query),
    retry: 0,
  });
};
