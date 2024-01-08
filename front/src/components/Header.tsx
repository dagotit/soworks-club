'use client';
import styles from '@/components/css/Header.module.css';
import { useGetAccessToken, useGetLogout } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import { useTokenStore } from '@/store/useLogin';
import { useDialogStore } from '@/store/useDialog';
const Header = (props: any) => {
  const getLogout = useGetLogout();
  const router = useRouter();
  const { accessToken, setAccessToken, setTokenExpires } = useTokenStore();
  const getAccessToken = useGetAccessToken();
  const { open, allClose } = useDialogStore();

  useEffect(() => {
    if (!accessToken) {
      getAccessToken.mutate(null, {
        onError: (err) => {
          console.log('err:::', err);
          open('alert', '로그아웃', '로그아웃 되었습니다.', () => {
            router.push('/login');
          });
        },
      });
    }
    return () => {
      allClose();
    };
  }, []);
  useEffect(() => {
    console.log('accessToken state:', accessToken);
    if (accessToken !== '') {
      props.propAttendanceCk(true);
    }
  }, [accessToken]);
  /**
   * @function
   * 로그아웃 api 호출
   */
  function handleLogout() {
    getLogout.mutate(null, {
      onSuccess: () => {
        setAccessToken('');
        setTokenExpires(0);
        router.push('/login');
      },
      onError: () => {
        open('alert', '로그아웃', '로그아웃 실패');
      },
    });
  }
  return (
    <header className={styles.header}>
      <button type="button" onClick={handleLogout}>
        로그아웃
      </button>
    </header>
  );
};
export default Header;
