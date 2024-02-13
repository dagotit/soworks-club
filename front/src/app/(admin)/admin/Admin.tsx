'use client';

import styles from './Admin.module.css';
import Link from 'next/link';

// TODO 회원 리스트가 있어야 회원 삭제 가능 < member id 확인을 위해서
const Admin = () => {
  return (
    <div className={styles.amdinNav}>
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
        <li>회원 찾기</li>
        <li>회원 삭제</li>
        <li>알림 등록</li>
      </ul>
    </div>
  );
};

export default Admin;
