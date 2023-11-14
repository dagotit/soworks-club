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
          {dialogList.length !== 0 &&
            dialogList.map((element, idx) => (
              <div key={idx}>
                <p>{element.title}</p>
                <p>{element.contents}</p>
                <button
                  onClick={() => {
                    check(element.action);
                  }}
                >
                  확인
                </button>
                {element.type === 'confirm' && (
                  <button onClick={cancel}>취소</button>
                )}
              </div>
            ))}
        </div>
      )}
    </Fragment>
  );
};

export default Dialog;
