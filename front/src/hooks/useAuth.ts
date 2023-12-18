import {
  apiGetAccessToken,
  apiGetEmailCodeVerify,
  apiLogin,
  apiLogout,
  apiPostCreditsEmail,
  getLogoImg,
} from '@/services/authService';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

/*
export const useGetUser = () =>
  useQuery({
    queryKey: ['get-user'],
    queryFn: logout,
    retry: false,
    staleTime: Infinity,
    // refetchOnWindowFocus: true,
    // enabled: false,
  });
*/

export const usePostCreditsEmail = () => {
  return useMutation({
    mutationKey: ['post-creditsEmail'],
    mutationFn: apiPostCreditsEmail,
  });
};
export const useGetEmailCodeVerfiy = () => {
  return useMutation({
    mutationKey: ['get-email-code-verify'],
    mutationFn: apiGetEmailCodeVerify,
  });
};
export const usePostLogin = () => {
  return useMutation({
    mutationKey: ['post-login'],
    mutationFn: apiLogin,
  });
};

export const useGetAccessToken = () => {
  return useMutation({
    mutationKey: ['get-access-token'],
    mutationFn: apiGetAccessToken,
  });
};

export const useGetLogout = () => {
  return useMutation({
    mutationKey: ['get-logout'],
    mutationFn: apiLogout,
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
