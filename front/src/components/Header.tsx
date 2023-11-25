'use client';

import { useEffect } from 'react';
import { useTokenStore } from '@/store/useLogin';
import { useGetAccessToken } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';

const Header = () => {
  const { accessToken } = useTokenStore();
  const getToken = useGetAccessToken();
  const router = useRouter();

  useEffect(() => {
    console.log('accessToken:', accessToken);
    if (accessToken.trim() === '') {
      getToken.mutate(null, {
        onSuccess: () => {},
        onError: (error) => {},
        onSettled: () => {},
      });
    }
  }, [accessToken]);
  return <header>헤더</header>;
};
export default Header;
