import { apiLogin, getLogoImg, logout } from '@/services/authService';
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
  });
};

export const useGetLogoImg = () => {
  useQuery({
    queryKey: ['get-logo'],
    queryFn: getLogoImg,
    retry: false,
    staleTime: Infinity,
  });
};
