'use client';

import styles from './Admin.module.css';
import Link from 'next/link';

// TODO 회원 리스트가 있어야 회원 삭제 가능 < member id 확인을 위해서
const Admin = () => {
  return (
    <div className={styles.adminNav}>
      <ul>
        <li>
          <Link
            href={{
              pathname: '/admin/userRegister',
            }}
          >
            직원(사용자) 등록
          </Link>
        </li>
        <li><Link
          href={{
            pathname: '/admin/userDelete',
          }}
        >
          직원(사용자) 삭제
        </Link></li>
        <li><Link
          href={{
            pathname: '/admin/alarm',
          }}
        >알림 등록</Link></li>
      </ul>
    </div>
  );
};

export default Admin;
