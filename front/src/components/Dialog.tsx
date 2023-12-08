'use client';

import styles from './Dialog.module.css';
import { useDialogStore } from '@/store/useDialog';
import { Fragment } from 'react';

const Dialog = () => {
  const { dialogList, check, cancel } = useDialogStore();

  return (
    <Fragment>
      {dialogList.length > 0 && <div className={styles.dimmed}></div>}
      {dialogList.length > 0 && (
        <div className={styles.dialogContents}>
          <p className={styles.fixTitle}>알림</p>
          {dialogList.length !== 0 &&
            dialogList.map((element, idx) => (
              <div key={idx} className={styles.contentsWrap}>
                <p>{element.title}</p>
                <p>{element.contents}</p>
                <div className={styles.btnWrap}>
                  <button
                    className={`${styles.btn} ${
                      element.type === 'alert' && styles.alert
                    }`}
                    onClick={() => {
                      check(element.action);
                    }}
                  >
                    확인
                  </button>
                  {element.type === 'confirm' && (
                    <button
                      className={`${styles.btn} ${styles.cancel}`}
                      onClick={cancel}
                    >
                      취소
                    </button>
                  )}
                </div>
              </div>
            ))}
        </div>
      )}
    </Fragment>
  );
};

export default Dialog;
