'use client';

import { Fragment, useEffect } from 'react';
import styles from '@/app/page.module.css';
import Image from 'next/image';
// import { useGetUser } from '@/hooks/useAuth';
import Loading from '@/components/Loading';
import { useRouter } from 'next/navigation';
import Header from '@/components/Header';
import Bg from '@/components/bgBox/Bg';
import { useGetAttendance } from '@/hooks/useCalendar';
import { useTokenStore } from '@/store/useLogin';
import { useAttendStore } from '@/store/useAttend';
import useDidMountEffect from '@/utils/useDidMountEffect';

const Main = () => {
  const { accessToken } = useTokenStore();
  const { todayAttend, isAttend, attendDayCheck } = useAttendStore();
  const attendanceCheck = useGetAttendance();
  const router = useRouter();
  // const { isLoading, isFetching, data, isError, error, refetch } = useGetUser();
  // const handlerBtn = () => {
  //   refetch().then((r) => {
  //     console.log('refetch');
  //   });
  // };
  /**
   * @function
   * header component 에서 이벤트 바인딩 > 출석체크 하기
   * [accessToken] 이 있어야 하기 때문에 이렇게 로직을 작성함..
   */
  const handleAttendanceCheck = (e: boolean | null | undefined) => {
    if (e && accessToken && !isAttend) {
      handleAttendanceApi();
    }
  };

  /**
   * @function
   * mounted 했을 때 출석체크 하루가 지났는지 체크
   */
  useEffect(() => {
    attendDayCheck();
  }, []);

  /**
   * @function
   * 하루가 지났을 때 출석체크 하는 api 를 실행하기 위한 메서드
   */
  useDidMountEffect(() => {
    if (!isAttend && accessToken) {
      handleAttendanceApi();
    }
  }, [isAttend]);

  const testBtn = async () => {
    router.push('/calendar');
  };

  /**
   * @function
   * 출석체크 api
   */
  const handleAttendanceApi = () => {
    attendanceCheck.mutate(null, {
      onSuccess: (resp: any) => {
        todayAttend();
      },
    });
  };

  // if (isLoading) {
  //   return <Loading />;
  // }

  return (
    <Fragment>
      <Bg />
      <Header propAttendanceCk={handleAttendanceCheck} />
      <main className={styles.main}>
        {/*<button type="button" onClick={handlerBtn}>*/}
        {/*  refetch*/}
        {/*</button>*/}
        <button onClick={testBtn}>api호출</button>
      </main>
    </Fragment>
  );
};
export default Main;
