import { useMutation, useQuery } from '@tanstack/react-query';
import {
  apiGetGroupInfo,
  apiGetSearchList,
  apiGetUpdateStatus,
} from '@/services/groupService';

export const useGetGroupInfo = (id: number) => {
  return useQuery({
    queryKey: ['get-group-info', id],
    queryFn: () => apiGetGroupInfo(id),
    retry: 0,
  });
};

export const useGetSearchList = () => {
  return useMutation({
    mutationKey: ['get-search-list'],
    mutationFn: apiGetSearchList,
  });
};

export const useGetUpdateStatus = () => {
  return useMutation({
    mutationKey: ['get-update-status'],
    mutationFn: apiGetUpdateStatus,
  });
};
