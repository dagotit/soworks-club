import {
  apiGetAccessToken,
  apiGetEmailCodeVerify,
  apiLogin,
  apiLogout,
  apiPostCreditsEmail,
  apiSignup,
  apiUpdatePassword,
  getLogoImg,
} from '@/services/authService';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

/*export const useGetUser = () =>
  useQuery({
    queryKey: ['get-user'],
    queryFn: apiGetMonthCalendar,
    retry: false,
    staleTime: Infinity,
    // refetchOnWindowFocus: true,
    // enabled: false,
  });*/

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
export const usePostChangePassword = () => {
  return useMutation({
    mutationKey: ['post-update-password'],
    mutationFn: apiUpdatePassword,
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

export const usePostSignup = () => {
  return useMutation({
    mutationKey: ['post-signup'],
    mutationFn: apiSignup,
  });
};
