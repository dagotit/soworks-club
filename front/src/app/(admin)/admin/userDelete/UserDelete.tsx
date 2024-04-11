'use client';

import styles from '@/app/(admin)/admin/Admin.module.css';
import { useGetMemberList } from '@/hooks/useAdmin';
import { useEffect, useState } from 'react';

const UserDelete = () => {
  const memberList = useGetMemberList();
  const [name, setName] = useState('');

  /**
   * @function
   * 회원 조회 [ api ]
   */
  function getMemberList() {
    const queryString = name === '' ? null : name;
    memberList.mutate(queryString, {
      onSuccess: (resp: any) => {
        console.log('회원 리스트??',resp)
      }
    })
  }
  useEffect(() => {
    getMemberList()
  }, []);

  return <div className={styles.amdinUserUpload}>
    <ul>
      <li>리스트?</li>
    </ul>
  </div>
}

export default UserDelete;