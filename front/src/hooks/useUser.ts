import { useMutation, useQuery } from '@tanstack/react-query';
import { apiGetAlarmList, apiGetAlarmRead, apiGetMyInfo } from '@/services/userService';

/**
 * @function
 * 내 정보 조회
 * 다른 곳에서 쓰일 것을 ... 고려해서 useQueries 로 묶지 않음..
 */
export const useGetMyInfo = (call: boolean) => {
  return useQuery({
    queryKey: ['get-my-info'],
    queryFn: apiGetMyInfo,
    enabled: call
  })
}

/**
 * @function
 * 나의 알림 조회
 */
export const useGetAlarmList = (isFetch: boolean) => {
  return useQuery({
    queryKey: ['get-alarm-list'],
    queryFn: apiGetAlarmList,
    refetchOnMount: true,
    enabled: isFetch,
    refetchInterval: 60000,
    // refetchIntervalInBackground: true
  })
}

/**
 * @function
 * 알림 상세보기
 */
export const useGetAlarmRead = () => {
  return useMutation({
    mutationKey: ['get-alarm-read'],
    mutationFn: apiGetAlarmRead,
  })
}