import { apiLogin, logout } from '@/services/authService';
import { useQuery } from '@tanstack/react-query';

export const useGetUser = () =>
  useQuery({
    queryKey: ['get-user'],
    queryFn: apiLogin,
    retry: false,
    refetchOnWindowFocus: true,
  });
