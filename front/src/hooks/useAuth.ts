import { apiLogin, logout } from '@/services/authService';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

export const useGetUser = () =>
  useQuery({
    queryKey: ['get-user'],
    queryFn: logout,
    retry: false,
    staleTime: Infinity,
    // refetchOnWindowFocus: true,
    // enabled: false,
  });

export const usePostLogin = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: ['post-login'],
    mutationFn: apiLogin,
    onSuccess: (data, variables, context) => {
      return JSON.stringify(data);
    },
  });
};
