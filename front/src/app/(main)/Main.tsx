'use client';

import { Fragment, useEffect } from 'react';
import styles from '@/app/page.module.css';
import Image from 'next/image';
// import { useGetUser } from '@/hooks/useAuth';
import Loading from '@/components/Loading';
import { useRouter } from 'next/navigation';
import Header from '@/components/Header';
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
   * mounted 했을 때 출석체크 하루가 지났는지 체크
   */
  useEffect(() => {
    attendDayCheck();
  }, []);
  /**
   * @function
   * mounted 최초진입시 실행하지 않아 추가
   * 하루가 지났을 때 출석체크 하는 api 를 실행하기 위한 메서드
   * */
  useEffect(() => {
    if (!isAttend) {
      handleAttendanceApi();
    }
  }, [isAttend]);

  const testBtn = async () => {
    router.push('/calendar');
  };
  const testBtn2 = async () => {
    router.push('/create');
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
      <Header />
      <main className={styles.homeMain}>
        {/*<button type="button" onClick={handlerBtn}>*/}
        {/*  refetch*/}
        {/*</button>*/}
        <section className={styles.mainSec01}>
          <div className={styles.mainImgBox}>
            <img src="/images/mainSection01Bg.png" alt="혼자가 아닌, 함께하는 회사 속에서 이루어지는 또 하나의 모임. 모두가 소중하게, 함께 가치있게" />
          </div>
          <div className={styles.mainSec01Txt}>
            <p>
              혼자가 아닌,<br />
              함께하는 회사 속에서<br />
              이루어지는 또 <span className={styles.mstBox}>하나의 모임</span>
            </p>
            <p>
              모두가 <span className={styles.mstBold}>소중하게</span>, 함께 <span className={styles.mstBold}>가치있게</span>
            </p>
          </div>
        </section>
        <section className={styles.mainSec02}>
          <div className={styles.mstWordWrap}>
            <div className={styles.mstwTit}>Create</div>
            <p className={styles.mstwTxt}>
              <span>
                공유하고싶은<br />나의 취미를 공유하세요
              </span>
            </p>
          </div>
          <div className={styles.mstCreImg}>
            <img src="/images/create.png" alt="공유하고 싶은 나의 취미들" />
          </div>
        </section>
        <section className={styles.mainSec03}>
          <div className={styles.mstWordWrap}>
            <div className={styles.mstwTit}>Active</div>
            <p className={styles.mstwTxt}>
              <span>
                다양한 활동을 즐기고<br />칭호 및 뱃지를 획득하세요
              </span>
            </p>
          </div>
          <div className={styles.mstCreImg}>
            <img src="/images/activeMedal.png" alt="금, 은, 동메달을 획득하세요" />
          </div>
        </section>
        <section className={styles.mainSec04}>
          <p>DAGACHI PLAY</p>
          <div>
            <button onClick={testBtn}>Go Calender</button>
            <button onClick={testBtn2}>Create Group</button>
          </div>
        </section>
      </main>
    </Fragment>
  );
};
export default Main;
