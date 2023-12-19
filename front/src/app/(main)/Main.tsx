'use client';

import { Fragment, useEffect } from 'react';
import styles from '@/app/page.module.css';
import Image from 'next/image';
// import { useGetUser } from '@/hooks/useAuth';
import Loading from '@/components/Loading';
import { useRouter } from 'next/navigation';
import Header from '@/components/Header';
import Bg from "@/components/bgBox/Bg";

const Main = () => {
  const router = useRouter();
  // const { isLoading, isFetching, data, isError, error, refetch } = useGetUser();
  // const handlerBtn = () => {
  //   refetch().then((r) => {
  //     console.log('refetch');
  //   });
  // };

  const testBtn = async () => {
    router.push('/login');
  };

  // if (isLoading) {
  //   return <Loading />;
  // }

  return (
    <Fragment>
      <Bg />
      {/*<Header />*/}
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
