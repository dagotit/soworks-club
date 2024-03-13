import { useMutation, useQuery } from '@tanstack/react-query';
import { apiGetAdminCheck, apiPostMemberUpload, apiPostTemplateDownLoad } from '@/services/adminService';

/**
 * @function
 * 관리자가 아닐 경우 admin 메뉴 숨기기
 * // TODO 나중에 메뉴 생기는 현상 수정
 */
export const useGetAdminCheck = () => {
  return useQuery({
    queryKey: ['get-admin-check'],
    queryFn: apiGetAdminCheck,
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

export const usePostTemplateDownLoad = () => {
  return useMutation({
    mutationKey: ['post-template-download'],
    mutationFn: apiPostTemplateDownLoad,
  })
}
