import {
  apiGetAttendance,
  apiGetMonthCalendar,
} from '@/services/calendarService';
import { useMutation, useQuery } from '@tanstack/react-query';

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
