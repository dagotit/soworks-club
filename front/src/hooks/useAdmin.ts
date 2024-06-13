import { useMutation, useQuery } from '@tanstack/react-query';
import {
  apiGetAdminCheck,
  apiGetMemberList, apiGetNoticeAlarm, apiPostMemberDelete,
  apiPostMemberUpload,
  apiPostTemplateDownLoad,
} from '@/services/adminService';

/**
 * @function
 * 관리자가 아닐 경우 admin 메뉴 숨기기
 * // TODO 나중에 메뉴 생기는 현상 수정
 */
export const useGetAdminCheck = (isfetch: boolean) => {
  // console.log('isfetch:::::', isfetch)
  return useQuery({
    queryKey: ['get-admin-check'],
    queryFn: apiGetAdminCheck,
    // staleTime: 60000, //  1분이 지난 후에 새로운 데이터가 필요한 상태
    enabled: isfetch, // true 일 경우만 api 실행
  });
};

/**
 * @function
 * 회원 일괄 업로드
 */
export const usePostMemberUpload = () => {
  return useMutation({
    mutationKey: ['post-member-upload'],
    mutationFn: apiPostMemberUpload,
  });
};
/**
 * @function
 * 일괄 업로드를 위한 템플릿 다운로드
 */
export const usePostTemplateDownLoad = () => {
  return useMutation({
    mutationKey: ['post-template-download'],
    mutationFn: apiPostTemplateDownLoad,
  })
}

/**
 * @function
 * 회원 전체 조회 + 이름으로 검색가능
 */
export const useGetMemberList = (name?: string | null, type?: string) => {
  return useQuery({
    queryKey: ['get-member-list', name],
    queryFn: async () => await apiGetMemberList(name, type),
  })
}
/**
 * @function
 * 회원 이메일로 검색
 */
/*export const useGetEmailMemberList = () => {
  return useMutation({
    mutationKey: ['get-email-member-list'],
    mutationFn: apiGetMemberEmailFind,
  })
}*/

/**
 * @function
 * 회원 삭제
 */
export const usePostMemberDelete = () => {
  return useMutation({
    mutationKey: ['post-member-delete'],
    mutationFn: apiPostMemberDelete,
  })
}

/**
 * @function
 * 알림 전송
 */
export const usePostAlarmSend = () => {
  return useMutation({
    mutationKey: ['post-alarm-send'],
    mutationFn: apiGetNoticeAlarm
  })
}