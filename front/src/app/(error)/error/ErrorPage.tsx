'use client';

import { useSearchParams } from 'next/navigation';
import { useEffect } from 'react';
import styles from './ErrorPage.module.css';

const ErrorPage = () => {
  const params = useSearchParams();
  const msgParam = params.get('msg');

  return <div className={styles.wrap}>{msgParam && <p>{msgParam}</p>}</div>;
};

export default ErrorPage;
