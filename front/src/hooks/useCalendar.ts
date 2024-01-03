import { apiGetMonthCalendar } from '@/services/calendarService';
import { useQuery } from '@tanstack/react-query';

export const useGetMonthCalendar = () => {
  return useQuery({
    queryKey: ['get-month-calendar'],
    queryFn: apiGetMonthCalendar,
    retry: false,
    staleTime: Infinity,
  });
};
