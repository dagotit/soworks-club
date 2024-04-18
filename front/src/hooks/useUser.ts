import { useQuery } from '@tanstack/react-query';
import { apiGetMyInfo } from '@/services/userService';

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