'use client';

import { usePostMemberUpload } from '@/hooks/useAdmin';
import React, { useCallback, useEffect, useState } from 'react';
import styles from '@/app/(admin)/admin/Admin.module.css';
import { useDialogStore } from '@/store/useDialog';
import Link from 'next/link';

const UserRegister = () => {
  const [pValue, setpValue] = useState(0);
  const [file, setFile] = useState<any>(null);
  const [uploadedFile, setUploadedFile] = useState({});
  const memberUpload = usePostMemberUpload();
  const { open, allClose } = useDialogStore();

  useEffect(() => {
    return () => {
      allClose();
    };
  }, []);

  /**
   * @function
   * input 파일선택
   */
  const onChangeFile = useCallback(
    (event: React.FormEvent<HTMLInputElement>) => {
      // @ts-ignore
      if (event.target.files.length <= 0) {
        setFile(null);
        return;
      }

      // @ts-ignore
      setFile(event.target.files[0]); // file 저장
    },
    [],
  );
  const progress = (progressEvent: any) => {
    let pert = (progressEvent.loaded * 100) / progressEvent.total;
    setpValue(pert / 100);
  };
  /**
   * @function
   * input 파일 업로드
   */
  const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!file) {
      // 파일 없으면
      open('alert', '직원등록', '파일을 선택해주세요.');
      return;
    }
    const formData = new FormData();
    // @ts-ignore
    formData.append('file', file);

    memberUpload.mutate(
      { formData, progress },
      {
        onSuccess: fileUploadSuccess,
      },
    );
  };

  /**
   * @function
   * 일괄 업로드 성공
   */
  const fileUploadSuccess = (res: any) => {
    console.log('file upload res', res);
    if (res.respCode === '00') {
      // 업로드 성공
      open('alert', '직원등록', res.respBody);
    }
  };

  return (
    <div className={styles.amdinUserUpload}>
      <form onSubmit={onSubmit}>
        <input type="file" accept=".xlsx, .xls, .csv" onChange={onChangeFile} />
        <input type="submit" value="upload" />
      </form>
      <progress value={pValue} />
      <Link
        href={{
          pathname: '/admin',
        }}
      >
        목록으로 이동
      </Link>
    </div>
  );
};

export default UserRegister;
