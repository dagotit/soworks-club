import { useMutation, useQueries, useQuery } from '@tanstack/react-query';
import {
  apiGetGroupAttendList,
  apiGetGroupInfo,
  apiGetSearchList,
  apiGetUpdateStatus,
  apiPostAttendApply,
  apiPostAttendCancel,
  apiPostGroupDelete,
} from '@/services/groupService';

/**
 * @function
 * 모임 상세 페이지
 */
export const useGetDetailGroup = (id: number) => {
  return useQueries({
    queries: [
      {
        queryKey: ['get-group-info', id],
        queryFn: () => apiGetGroupInfo(id),
        staleTime: 0,
      },
      {
        queryKey: ['get-attend-group', id],
        queryFn: () => apiGetGroupAttendList(id),
        staleTime: 0,
      },
    ],
  });
};

/**
 *
 */
export const useGetAttendGroup = () => {
  return useMutation({
    mutationKey: ['get-attend-group'],
    mutationFn: apiGetGroupAttendList,
  });
};

/**
 * @function
 * 상단 헤더 검색
 */
export const useGetSearchList = () => {
  return useMutation({
    mutationKey: ['get-search-list'],
    mutationFn: apiGetSearchList,
  });
};

/**
 * @function
 * 모임 상태 변경
 */
export const useGetUpdateStatus = () => {
  return useMutation({
    mutationKey: ['get-update-status'],
    mutationFn: apiGetUpdateStatus,
  });
};

/**
 * @function
 * 모임 참가 신청하기
 */
export const usePostAttendApply = () => {
  return useMutation({
    mutationKey: ['post-attend-apply'],
    mutationFn: apiPostAttendApply,
  });
};

/**
 * @function
 * 모임 참가 취소하기
 */

export const usePostAttendCancel = () => {
  return useMutation({
    mutationKey: ['post-attend-cancel'],
    mutationFn: apiPostAttendCancel,
  });
};

/**
 * @function
 * 모임삭제
 */
export const usePostGroupDelete = () => {
  return useMutation({
    mutationKey: ['post-group-delete'],
    mutationFn: apiPostGroupDelete,
  });
};
