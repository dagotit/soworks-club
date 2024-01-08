import {
  apiGetAttendance,
  apiGetMonthCalendar,
} from '@/services/calendarService';
import { useMutation, useQuery } from '@tanstack/react-query';

export const useGetMonthCalendar = () => {
  return useQuery({
    queryKey: ['get-month-calendar'],
    queryFn: apiGetMonthCalendar,
    retry: false,
    staleTime: Infinity,
  });
};

export const useGetAttendance = () => {
  return useMutation({
    mutationKey: ['get-attendance'],
    mutationFn: apiGetAttendance,
  });
};
