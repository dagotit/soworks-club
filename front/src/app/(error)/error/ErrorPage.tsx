'use client';

import { useSearchParams } from 'next/navigation';
import { useEffect } from 'react';
import styles from './ErrorPage.module.css';

const ErrorPage = () => {
  const params = useSearchParams();

  const msgParam = params.get('msg');
  useEffect(() => {
    console.log('params:', msgParam);
  }, []);
  return (
    <div className={styles.wrap}>
      <p>{msgParam}</p>
    </div>
  );
};

export default ErrorPage;
