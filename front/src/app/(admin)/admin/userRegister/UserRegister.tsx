'use client';

import { usePostMemberUpload, usePostTemplateDownLoad } from '@/hooks/useAdmin';
import React, { useCallback, useEffect, useState } from 'react';
import styles from '@/app/(admin)/admin/Admin.module.css';
import { useDialogStore } from '@/store/useDialog';
import Link from 'next/link';

const UserRegister = () => {
  const [pValue, setpValue] = useState(0);
  const [file, setFile] = useState<any>(null);
  const [uploadedFile, setUploadedFile] = useState({});
  const memberUpload = usePostMemberUpload();
  const templateDownload = usePostTemplateDownLoad()
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
      // TODO 리스트로 이동할지 결정 필요
    }
  };

  /**
   * @function
   * 템플릿 이름 추출
   */
  const extractDownloadFilename = (response: any) => {
    const disposition = response.headers["content-disposition"];
    return decodeURI(
      disposition
        .match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)[1]
        .replace(/['"]/g, "")
    );
  };

  /**
   * @function
   * 템플릿 다운로드
   */
  const onClicktemplateDownload = () => {
    templateDownload.mutate(null,
      {
        onSuccess: (res) => {
          // @ts-ignore
          const blob = new Blob([res.data], {type: res.data.type});
          // 특정 타입을 정의해야 경우에는 옵션을 사용해 MIME 유형을 정의 할 수 있습니다.
          // const blob = new Blob([this.content], {type: 'text/plain'})

          // blob을 사용해 객체 URL을 생성합니다.
          const fileObjectUrl = window.URL.createObjectURL(blob);

          const link = document.createElement("a");
          link.href = fileObjectUrl;
          link.style.display = "none";

          // 다운로드 파일 이름을 지정 할 수 있습니다.
          // 일반적으로 서버에서 전달해준 파일 이름은 응답 Header의 Content-Disposition에 설정 된다.
          link.download  = extractDownloadFilename(res)

          document.body.appendChild(link);
          link.click();
          link.remove();

          // 다운로드가 끝난 리소스(객체 URL)를 해제합니다.
          window.URL.revokeObjectURL(fileObjectUrl);
        },
      },)
  }



  return (
    <div className={styles.amdinUserUpload}>
      <form onSubmit={onSubmit}>
        <input type="file" accept=".xlsx, .xls, .csv" onChange={onChangeFile} />
        <input type="submit" value="upload" />
      </form>
      <progress value={pValue} />
      <button type="button" onClick={onClicktemplateDownload}>템플릿 다운로드</button>
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
