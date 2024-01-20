import { useQuery } from '@tanstack/react-query';
import { apiGetGroupInfo } from '@/services/groupService';

export const useGetGroupInfo = (id: number) => {
  return useQuery({
    queryKey: ['get-group-info', id],
    queryFn: () => apiGetGroupInfo(id),
    retry: 0,
  });
};
